package com.helia.oauth.repository

import com.helia.oauth.model.clientdetail.ClientDetailEntity
import com.helia.oauth.model.clientdetail.RedirectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Suppress("unused")
@Repository
interface RedirectRepository : JpaRepository<RedirectEntity, Long>