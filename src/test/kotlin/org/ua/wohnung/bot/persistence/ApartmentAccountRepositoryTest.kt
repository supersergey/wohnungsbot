package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent.inject
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.user.model.BundesLand
import org.ua.wohnung.bot.util.aFullUserDetails
import org.ua.wohnung.bot.util.anAccount
import org.ua.wohnung.bot.util.anApartment

@ExtendWith(JooqExtension::class)
internal class ApartmentAccountRepositoryTest {

    private val accountRepository: AccountRepository by inject(AccountRepository::class.java)
    private val userDetailsRepository: UserDetailsRepository by inject(UserDetailsRepository::class.java)
    private val apartmentRepository: ApartmentRepository by inject(ApartmentRepository::class.java)
    private val apartmentAccountRepository: ApartmentAccountRepository by inject(ApartmentAccountRepository::class.java)

    @Test
    fun findAccountsByApartmentId() {
        val account = anAccount()
        val userDetails = aFullUserDetails(account.id, BundesLand.BERLIN)
        val apartment = anApartment(bundesLand = BundesLand.BERLIN)

        accountRepository.save(account)
        userDetailsRepository.save(userDetails)
        apartmentRepository.save(apartment)

        apartmentAccountRepository.save(apartment.id, account.id)

        val accounts = apartmentAccountRepository.findAccountsByApartmentId(apartment.id, 0, 100)
        assertThat(accounts).singleElement()
        assertThat(accounts.first().account.id).isEqualTo(account.id)
    }
}
