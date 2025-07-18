package com.nlhdev.authentication.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "refresh_token")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true)
    val username: String,
    val hashedRefreshToken: String,
    val createdAt: Instant = Instant.now(),
    val expireAt: Instant
){
    constructor() : this(0, "", "", Instant.now(), Instant.now())
}