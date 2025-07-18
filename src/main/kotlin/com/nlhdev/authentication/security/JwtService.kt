package com.nlhdev.authentication.security

import io.jsonwebtoken.ClaimJwtException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey
import kotlin.io.encoding.ExperimentalEncodingApi

@Component
class JwtService(@Value("\${jwt.secret}") private val jwtKey: String) {

    val accessTokenTime: Long = 15 * 60 * 1000
    val refreshTokenTime: Long = 7 * 24 * 60 * 60 * 1000

    @OptIn(ExperimentalEncodingApi::class)
    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey))
    }

    private fun generateToken(username: String, tokenType: String, expiryTime: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + expiryTime)
        return Jwts.builder()
            .setSubject(username)
            .claim("token_type", tokenType)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateAccessToken(username: String): String {
        return generateToken(username = username, tokenType = "accessToken", expiryTime = accessTokenTime)
    }

    fun generateRefreshToken(username: String): String {
        return generateToken(username = username, tokenType = "refreshToken", expiryTime = refreshTokenTime)
    }

    fun getClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ClaimJwtException) {
            null
        }
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = getClaims(token = token) ?: return false
        val tokenType = claims["token_type"] as? String ?: return false
        return tokenType == "accessToken"
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = getClaims(token = token) ?: return false
        val tokenType = claims["token_type"] as? String ?: return false
        return tokenType == "refreshToken"
    }

    fun extractUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body.subject
    }

    fun validUsername(token: String, username: String): Boolean {
        return extractUsername(token = token) == username
    }
}