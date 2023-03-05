package com.helia.oauth.repository

import com.helia.oauth.model.clientdetail.ClientDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Suppress("unused")
@Repository
interface ClientDetailRepository : JpaRepository<ClientDetailEntity, Long>{
    fun findByClientId(clientId:String): ClientDetailEntity?
}