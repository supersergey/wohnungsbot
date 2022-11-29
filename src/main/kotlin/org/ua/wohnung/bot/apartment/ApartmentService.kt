package org.ua.wohnung.bot.apartment

import mu.KotlinLogging
import org.jooq.DSLContext
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentAccountRepository
import org.ua.wohnung.bot.persistence.ApartmentApplication
import org.ua.wohnung.bot.persistence.ApartmentRepository
import org.ua.wohnung.bot.persistence.ApartmentSearchCriteria
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.sheets.RowMapper
import org.ua.wohnung.bot.sheets.SheetReader
import org.ua.wohnung.bot.user.model.BundesLand
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ApartmentService(
    private val dslContext: DSLContext,
    private val accountRepository: AccountRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val apartmentRepository: ApartmentRepository,
    private val apartmentAccountRepository: ApartmentAccountRepository,
    private val sheetReader: SheetReader,
    private val rowMapper: RowMapper,
    private val messageSource: MessageSource
) {
    private val logger = KotlinLogging.logger { }
    private val timer = Timer()
    private val lock: Lock = ReentrantLock()

    init {
        timer.schedule(
            DbUpdateTask(),
            Duration.ofMinutes(0).toMillis(),
            Duration.ofMinutes(5).toMillis()
        )
    }

    inner class DbUpdateTask : TimerTask() {
        override fun run() {
            update()
        }
    }

    fun update(): Int {
        runCatching {
            lock.withLock {
                val incomingApartments = sheetReader.readRows().mapNotNull(rowMapper)
                dslContext.transaction { ctx ->
                    val activeApartments = apartmentRepository
                        .findByCriteria(ApartmentSearchCriteria(publicationStatus = PublicationStatus.ACTIVE))
                    val incomingApartmentsIds = incomingApartments.map { it.id }
                    val inactiveApartments = activeApartments
                        .filterNot { it.id in incomingApartmentsIds }
                        .onEach { it.publicationstatus = PublicationStatus.NOT_ACTIVE.name }
                    apartmentRepository.saveAll(ctx.dsl(), incomingApartments)
                    apartmentRepository.saveAll(ctx.dsl(), inactiveApartments)
                }
            }
        }.onFailure {
            logger.error(it) {}
        }
        return count().also {
            logger.info { "DB updated, $it active records" }
        }
    }

    private fun count(): Int = apartmentRepository.count()

    fun findById(apartmentId: String): Apartment? = apartmentRepository.findById(apartmentId)

    fun findByUserDetails(userId: Long): List<Apartment> {
        val userDetails = userDetailsRepository.findById(userId) ?: run {
            logger.warn { "$userId not found" }
            return emptyList()
        }
        return apartmentRepository.findByCriteria(
            ApartmentSearchCriteria(
                bundesLand = BundesLand.values().firstOrNull { it.germanName == userDetails.bundesland },
                numberOfTenants = userDetails.numberOfTenants.toInt(),
                petsAllowed = if (!userDetails.pets) null else true,
                publicationStatus = PublicationStatus.ACTIVE,
                wbs = if (userDetails.wbs) null else false,
                numberOfRooms = if (userDetails.wbs && userDetails.wbsNumberOfRooms != null)
                    userDetails.wbsNumberOfRooms
                else
                    null
            )
        ).toList()
    }

    fun acceptUserApartmentRequest(userId: Long, apartmentId: String): ApartmentRequestResult {
        val account = accountRepository.findById(userId) ?: throw ServiceException.UserNotFound(userId)
        if (account.role != Role.USER) throw ServiceException.AccessViolation(userId, account.role, Role.USER)
        apartmentRepository.findById(apartmentId)?.takeIf {
            PublicationStatus.valueOf(it.publicationstatus) == PublicationStatus.ACTIVE
        } ?: return ApartmentRequestResult.Failure("Помешкання не знайдено: $apartmentId")
        val apartmentRequestResult = apartmentAccountRepository
            .findLatestApplyTs(userId)
            .assertUserApplicationRateLimit()
        if (apartmentRequestResult is ApartmentRequestResult.Success &&
            userId.hasNotAlreadyAppliedForApartment(apartmentId)
        ) {
            apartmentAccountRepository.save(apartmentId, userId)
        }
        return apartmentRequestResult
    }

    private fun Long.hasNotAlreadyAppliedForApartment(apartmentId: String): Boolean =
        !apartmentAccountRepository.isUserAlreadyAppliedForApartment(apartmentId, this)

    fun countApplicationByApartmentId(apartmentId: String): Int =
        apartmentAccountRepository.countAccountsByApartmentId(apartmentId)

    fun findApplicantsByApartmentId(apartmentId: String, offset: Int, limit: Int = 1): List<ApartmentApplication> =
        apartmentAccountRepository.findAccountsByApartmentId(apartmentId, offset, limit)

    fun hideApplication(apartmentId: String, userId: Long) {
        apartmentAccountRepository.hideApplication(apartmentId, userId)
    }

    private fun List<OffsetDateTime>.assertUserApplicationRateLimit(
        limit: Int = 2,
        threshold: Duration = Duration.ofDays(1)
    ): ApartmentRequestResult {
        if (this.size == limit && Duration.between(this.last(), OffsetDateTime.now()) < threshold) {
            return ApartmentRequestResult.Failure(
                messageSource[FlowStep.REGISTERED_USER_REQUEST_DECLINED].formatWithNextTimestamp(last(), threshold)
            )
        }
        return ApartmentRequestResult.Success
    }

    private fun String.formatWithNextTimestamp(last: OffsetDateTime, threshold: Duration): String =
        last.plusDays(threshold.toDays())
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
            .let { format(it) }
}

sealed class ApartmentRequestResult {
    object Success : ApartmentRequestResult()
    class Failure(val cause: String) : ApartmentRequestResult()
}
