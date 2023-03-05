package com.helia.oauth.repository

import com.helia.oauth.model.user.UserAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository: JpaRepository<UserAccountEntity, Long>{
    fun findByUserName(userName:String):UserAccountEntity?
}