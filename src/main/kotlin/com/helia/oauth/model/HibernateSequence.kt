package com.helia.oauth.model

import javax.persistence.*

@Table(name = "hibernate_sequence")
@Entity
class HibernateSequence(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "next_val")
        var nextVal: Long? = null
)