package com.helia.oauth.model.user

import javax.persistence.*

@Entity
@Table(name = "role")
data class RoleEntity (
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id")
    var roleId:Long?=null,
    @Column(name = "role_name")
    var name:String?=null,

    @ManyToMany(mappedBy = "userRoleEntity")
    var userAccountEntities: MutableSet<UserAccountEntity>?=null

     )