package com.helia.oauth.model.clientdetail


import javax.persistence.*

@Entity
@Table(name = "scope")
class ScopeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "scope_id")
    var scopeId:Long?=null
    @Column(name = "scope_name")
    var name:String?=null

    @ManyToMany(mappedBy = "clientScopes")
    var clientDetails: MutableSet<ClientDetailEntity>?=null
}

