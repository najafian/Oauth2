package com.helia.oauth.model.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserAccountDetailEntity(private val userAccountEntity: UserAccountEntity) : UserDetails {

    fun getUserAccount()=userAccountEntity

    override fun getAuthorities(): Collection<GrantedAuthority?> =
         AuthorityUtils.createAuthorityList(*userAccountEntity.userRoleEntity!!.map { it.name }.toTypedArray())

    override fun getPassword()=
         userAccountEntity.password!!

    override fun getUsername()=
         userAccountEntity.email!!

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}