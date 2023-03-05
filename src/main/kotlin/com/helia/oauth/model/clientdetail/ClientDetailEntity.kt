package com.helia.oauth.model.clientdetail


import javax.persistence.*

@Entity
@Table(name = "client_detail")
class ClientDetailEntity(

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?=null,
    @Column(name = "resource_id")
    var resourceIds: String?=null,


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "client_scopes",
        joinColumns = [JoinColumn(name = "client_detail_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")]
    )
    var clientScopes: MutableSet<ScopeEntity>? =  mutableSetOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "client_redirect",
        joinColumns = [JoinColumn(name = "client_detail_id")],
        inverseJoinColumns = [JoinColumn(name = "redirect_id")]
    )
    var clientRedirects: MutableSet<RedirectEntity>? =  mutableSetOf(),


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "client_authority",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    var clientAuthorities: MutableSet<AuthorityEntity>? =  mutableSetOf(),
    @Column(name = "client_id")
    var clientId: String?=null,
    @Column(name = "client_secret")
    var clientSecret: String?=null,
    @Column(name = "grant_type")
    var grantType: String?=null
)
