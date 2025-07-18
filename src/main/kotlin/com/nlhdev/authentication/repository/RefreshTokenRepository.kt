package com.nlhdev.authentication.repository

import com.nlhdev.authentication.entity.RefreshToken
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByUsernameAndHashedRefreshToken(username: String, hashedRefreshToken: String): RefreshToken?
    fun deleteByUsernameAndHashedRefreshToken(username: String, hashedRefreshToken: String)

}