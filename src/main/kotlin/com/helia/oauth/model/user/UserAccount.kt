package com.helia.oauth.model.user

import java.time.ZonedDateTime

class UserAccount {
    var name: String? = null
    var lastName: String? = null
    var email: String = ""
    var password: String? = null
    var roles: List<String> = mutableListOf()
    var userId: Long? = null
    var appId: Long? = null
    var companyId: Long? = null
    var isLocked: Boolean = false
    var expirationDate: ZonedDateTime? = null
    var isEnabled: Boolean? = false
    var created: ZonedDateTime? = null
}