package com.helia.oauth.model.clientdetail

import javax.persistence.*



@Entity
@Table(name = "redirect")
class RedirectEntity (
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "redirect_id")
    var redirectId:Long?=null,
    @Column(name = "url")
    var url:String?=null,

    @ManyToMany(mappedBy = "clientRedirects")
    var clientDetails: MutableSet<ClientDetailEntity>?= mutableSetOf()
)