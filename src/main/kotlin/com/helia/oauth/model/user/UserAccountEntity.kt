package com.helia.oauth.model.user

import com.webhead.common.user.UserAccount
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "user_account")
class UserAccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var userId: Long? = null,

    @Column(name = "user_name")
    var userName: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var userRoleEntity: MutableSet<RoleEntity>? = null,

    @Column(name = "app_id")
    var appId: Long? = null,

    @Column(name = "company_id")
    var companyId: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "lastName")
    var lastName: String? = null,

    @Column(name = "locked")
    var isLocked: Boolean? = null,

    @Column(name = "expirationDate")
    var expirationDate: ZonedDateTime? = null,

    @Column(name = "enabled")
    var isEnabled: Boolean? = null,

    @Column(name = "created")
    var created: ZonedDateTime? = null,

    @Column(name = "deleted")
    var deleted: ZonedDateTime? = null
){
    fun toAccount(): UserAccount {
        val userAccount = UserAccount()
        userAccount.name = this.name.toString()
        userAccount.lastName = this.lastName.toString()
        userAccount.email = this.email.toString()
        userAccount.password = this.password.toString()
        userAccount.roles = userRoleEntity!!.map { it.name!! }
        userAccount.appId = appId
        userAccount.userId = this.userId
        userAccount.companyId = this.companyId
        userAccount.created = created
        userAccount.isEnabled = isEnabled
        userAccount.expirationDate = expirationDate
        userAccount.isLocked = isLocked
        return userAccount
    }
}

