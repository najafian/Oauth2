package com.helia.oauth.account

import com.webhead.common.user.UserAccount
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import java.time.ZonedDateTime

class AccountUserDetails(var userAccount: UserAccount) : UserDetails {
    val serialVersionUID=644897555146388017

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return AuthorityUtils.createAuthorityList(*this.userAccount.roles.toTypedArray())
    }

    override fun getPassword(): String {
        return userAccount.password
    }

    override fun getUsername(): String {
        return userAccount.email
    }

    override fun isAccountNonExpired(): Boolean {
        return userAccount.expirationDate>ZonedDateTime.now()
    }

    override fun isAccountNonLocked(): Boolean {
        return !userAccount.isLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return  userAccount.isEnabled
    }

    override fun isEnabled(): Boolean {
        return userAccount.isEnabled
    }

}