package org.ua.wohnung.bot.apartment

import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentAccountRepository
import org.ua.wohnung.bot.persistence.ApartmentRepository
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.util.aFullUserDetails
import org.ua.wohnung.bot.util.anAccount
import org.ua.wohnung.bot.util.anApartment

@ExtendWith(JooqExtension::class)
internal class ApartmentServiceITTest {

    private val accountRepository: AccountRepository by KoinJavaComponent.inject(AccountRepository::class.java)
    private val userDetailsRepository: UserDetailsRepository by KoinJavaComponent.inject(UserDetailsRepository::class.java)
    private val apartmentRepository: ApartmentRepository by KoinJavaComponent.inject(ApartmentRepository::class.java)
    private val apartmentAccountRepository: ApartmentAccountRepository by KoinJavaComponent.inject(
        ApartmentAccountRepository::class.java
    )

    private lateinit var apartmentService: ApartmentService

    @BeforeEach
    internal fun setUp() {
        apartmentService = ApartmentService(
            mockk(relaxed = true),
            accountRepository,
            userDetailsRepository,
            apartmentRepository,
            apartmentAccountRepository,
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
    }

    @Test
    fun acceptUserApartmentRequest() {
        val apartmentId = 1L
        val user = anAccount()
        val userDetails = aFullUserDetails(user.id)
        accountRepository.save(user)
        userDetailsRepository.save(userDetails)
        apartmentRepository.save(anApartment(id = apartmentId))
        apartmentAccountRepository.save(apartmentId.toString(), user.id)
        assertDoesNotThrow {
            apartmentService.acceptUserApartmentRequest(user.id, apartmentId = apartmentId.toString())
        }
    }
}
