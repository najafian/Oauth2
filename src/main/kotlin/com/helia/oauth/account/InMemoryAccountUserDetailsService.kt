package com.helia.oauth.account

import com.helia.oauth.repository.UserAccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class InMemoryAccountUserDetailsService(val accountUserAccountRepository: UserAccountRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountUserAccountRepository.findByUserName(username)
            ?: throw UsernameNotFoundException(String.format("%s is not found.", username))
        return AccountUserDetails(account.toAccount())
    }
}