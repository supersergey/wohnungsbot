package org.ua.wohnung.bot.apartment

import mu.KotlinLogging
import org.ua.wohnung.bot.exception.ServiceException
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

class ApartmentService(
    private val accountRepository: AccountRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val apartmentRepository: ApartmentRepository,
    private val apartmentAccountRepository: ApartmentAccountRepository,
    private val sheetReader: SheetReader,
    private val rowMapper: RowMapper
) {

    private val logger = KotlinLogging.logger { }

    fun update() {
        val apartments = sheetReader.readRows().mapNotNull(rowMapper)
        apartmentRepository.saveAll(apartments)
    }

    fun count(): Int = apartmentRepository.count()

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
                publicationStatus = PublicationStatus.ACTIVE
            )
        ).toList()
    }

    fun acceptUserApartmentRequest(userId: Long, apartmentId: String) {
        val account = accountRepository.findById(userId) ?: throw ServiceException.UserNotFound(userId)
        if (account.role != Role.USER) throw ServiceException.AccessViolation(userId, account.role, Role.USER)
        apartmentRepository.findById(apartmentId)?.takeIf {
            PublicationStatus.valueOf(it.publicationstatus) == PublicationStatus.ACTIVE
        } ?: throw ServiceException.ApartmentNotFound(apartmentId)
        apartmentAccountRepository
            .findLatestApplyTs(userId)
            .assertUserApplicationRateLimit(userId)
        if (apartmentAccountRepository.findAccountsByApartmentId(apartmentId).none { it.account.id == userId }) {
            apartmentAccountRepository.save(apartmentId, userId)
        }
    }

    fun findApplicantsByApartmentId(apartmentId: String): List<ApartmentApplication> {
        return apartmentAccountRepository.findAccountsByApartmentId(apartmentId)
    }

    private fun List<OffsetDateTime>.assertUserApplicationRateLimit(
        userId: Long,
        limit: Int = 2,
        duration: Duration = Duration.ofDays(1)
    ): List<OffsetDateTime> {
        if (this.size == limit && Duration.between(this.first(), OffsetDateTime.now()) < duration) {
            throw ServiceException.UserApplicationRateExceeded(userId, limit)
        }
        return this
    }
}
