package com.helia.oauth.model.clientdetail

import javax.persistence.*



@Entity
@Table(name = "authority")
class AuthorityEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "authority_id")
    var id:Long?=null
    @Column(name = "name")
    var name:String?=null
    @ManyToMany(mappedBy = "clientAuthorities")
    var userAccountEntities: MutableSet<ClientDetailEntity>?=null
}