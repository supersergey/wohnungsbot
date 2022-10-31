package org.ua.wohnung.bot.user

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent.inject
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentAccountRepository
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.util.aFullUserDetails
import kotlin.random.Random.Default.nextLong

@ExtendWith(JooqExtension::class, MockKExtension::class)
internal class UserServiceTest {

    private val accountRepository: AccountRepository by inject(AccountRepository::class.java)
    private val userDetailsRepository: UserDetailsRepository by inject(UserDetailsRepository::class.java)
    private val jooq: DSLContext by inject(DSLContext::class.java)

    @MockK
    private lateinit var mockUserDetailsRepository: UserDetailsRepository

    @MockK
    private lateinit var mockApartmentAccountRepository: ApartmentAccountRepository

    private lateinit var userService: UserService

    @BeforeEach
    internal fun setUp() {
        userService = UserService(
            accountRepository,
            mockUserDetailsRepository,
            mockApartmentAccountRepository,
            jooq,
            mockk(relaxed = true)
        )
    }

    @Test
    fun `should not delete a user if user details could not be deleted`() {
        val userDetails = aFullUserDetails()
        accountRepository.save(Account(userDetails.id, nextLong(), "username", Role.ADMIN))
        userDetailsRepository.save(userDetails)
        every { mockUserDetailsRepository.deleteById(any()) } throws Exception("")

        assertThrows<Exception> { userService.delete(userDetails.id) }

        assertThat(userDetailsRepository.findById(userDetails.id)).isNotNull
        assertThat(accountRepository.findById(userDetails.id)).isNotNull
    }
}
