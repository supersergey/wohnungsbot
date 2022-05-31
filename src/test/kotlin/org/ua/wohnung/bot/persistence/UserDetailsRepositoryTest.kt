package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.koin.java.KoinJavaComponent.inject
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.util.aFullUserDetails
import org.ua.wohnung.bot.util.aPartialUserDetails
import java.util.stream.Stream

@ExtendWith(JooqExtension::class)
internal class UserDetailsRepositoryTest {

    private val accountRepository: AccountRepository by inject(AccountRepository::class.java)
    private val userDetailsRepository: UserDetailsRepository by inject(UserDetailsRepository::class.java)

    @ParameterizedTest
    @MethodSource("generateUserDetails")
    fun `should save the user details`(userDetails: UserDetails) {
        accountRepository.save(
            Account(userDetails.username, Role.USER)
        )
        userDetailsRepository.save(userDetails)
        userDetailsRepository.save(userDetails)
        val saved = userDetailsRepository.findById(userDetails.username)
        assertThat(saved).usingRecursiveComparison().isEqualTo(userDetails)
    }

    @Test
    fun `should not allow to save user details if user account does not contain a user`() {
        assertThrows<Exception> {
            userDetailsRepository.save(aFullUserDetails())
        }
    }

    @Test
    fun `should delete an entry by id`() {
        val userDetails = aFullUserDetails()
        accountRepository.save(Account(userDetails.username, Role.USER))
        userDetailsRepository.save(userDetails)
        userDetailsRepository.deleteById(userDetails.username)

        assertThat(userDetailsRepository.findById(userDetails.username)).isNull()
        assertThat(accountRepository.findById(userDetails.username)).isNotNull
    }

    companion object {
        @JvmStatic
        fun generateUserDetails(): Stream<Arguments> = Stream.of(
            Arguments.of(aFullUserDetails()),
            Arguments.of(aPartialUserDetails())
        )
    }
}