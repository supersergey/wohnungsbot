package org.ua.wohnung.bot.apartment

import mu.KotlinLogging
import org.ua.wohnung.bot.persistence.ApartmentRepository
import org.ua.wohnung.bot.persistence.ApartmentSearchCriteria
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.sheets.RowMapper
import org.ua.wohnung.bot.sheets.SheetReader
import org.ua.wohnung.bot.user.UserService
import org.ua.wohnung.bot.user.model.BundesLand

class ApartmentService(
    private val userService: UserService,
    private val apartmentRepository: ApartmentRepository,
    private val sheetReader: SheetReader,
    private val rowMapper: RowMapper
) {

    private val logger = KotlinLogging.logger {  }

    fun update() {
        val apartments = sheetReader.readRows().mapNotNull(rowMapper)
        apartmentRepository.saveAll(apartments)
    }

    fun count(): Int = apartmentRepository.count()

    fun findByUserDetails(userId: Int): List<Apartment> {
        val userDetails = userService.findById(userId) ?: run {
            logger.warn { "$userId not found" }
            return emptyList()
        }
        return apartmentRepository.findByCriteria(
            ApartmentSearchCriteria(
                bundesLand = BundesLand.values().firstOrNull { it.germanName == userDetails.bundesland },
                numberOfTenants = userDetails.numberOfTenants.toInt(),
                petsAllowed = userDetails.pets,
//                publicationStatus = PublicationStatus.ACTIVE // todo
            )
        ).toList()
    }
}
