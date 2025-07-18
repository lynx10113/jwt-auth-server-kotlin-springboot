package com.nlhdev.authentication.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "authenticated_users")
data class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @Column(unique = true)
    val email: String,

    @Column(unique = true)
    val username: String,

    var hashedPassword: String?,
) {
    constructor() : this(0,"","","")
}