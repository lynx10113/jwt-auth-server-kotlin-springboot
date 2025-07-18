package com.nlhdev.authentication.service

import com.nlhdev.authentication.mapper.AuthMapper
import com.nlhdev.authentication.dto.AuthRequest
import com.nlhdev.authentication.dto.AuthResponse
import com.nlhdev.authentication.dto.ChangePasswordRequest
import com.nlhdev.authentication.entity.RefreshToken
import com.nlhdev.authentication.dto.RefreshTokenRequest
import com.nlhdev.authentication.dto.RegisterRequest
import com.nlhdev.authentication.dto.RegisterResponse
import com.nlhdev.authentication.dto.TokenPair
import com.nlhdev.authentication.dto.UserDetail
import com.nlhdev.authentication.entity.User
import com.nlhdev.authentication.exception.InvalidCredentialsException
import com.nlhdev.authentication.exception.UsernameAlreadyExistsException
import com.nlhdev.authentication.repository.RefreshTokenRepository
import com.nlhdev.authentication.repository.UserRepository
import com.nlhdev.authentication.security.JwtService
import io.jsonwebtoken.io.Encoders
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authMapper: AuthMapper
) : AuthService {

    override fun register(request: RegisterRequest): RegisterResponse {
        if (userRepository.findByUsername(request.username) != null)
            throw UsernameAlreadyExistsException("Username already exists")
        val encodedPassword = passwordEncoder.encode(request.password)
        userRepository.save(User(email = request.email, username = request.username, hashedPassword = encodedPassword))
        return RegisterResponse(statusCode = HttpStatus.CREATED.value().toString(), status = "Success")
    }

    override fun authenticate(request: AuthRequest): AuthResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw InvalidCredentialsException("Invalid credentials")

        if (!passwordEncoder.matches(request.password, user.hashedPassword))
            throw InvalidCredentialsException("Invalid credentials")

        val accessToken = jwtService.generateAccessToken(user.username)
        val refreshToken = jwtService.generateRefreshToken(user.username)
        saveRefreshToken(user.username, rawRefreshToken = refreshToken)
        return AuthResponse(
            statusCode = HttpStatus.OK.value().toString(),
            status = "Success",
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun getUserInfo(username: String): UserDetail? {
        val user = userRepository.findByUsername(username = username)
            ?: throw UsernameNotFoundException("User doesn't exist")
        val mappedUser = authMapper.mapToDomain(user)
        return mappedUser
    }

    @Transactional
    override fun refreshToken(rawRefreshToken: RefreshTokenRequest): TokenPair {
        if (!jwtService.validateRefreshToken(rawRefreshToken.refreshToken))
            throw IllegalArgumentException("Invalid refresh token")
        val username = jwtService.extractUsername(rawRefreshToken.refreshToken)
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("Invalid refresh token")
        val hashedToken = hashRefreshToken(rawRefreshToken.refreshToken)
        refreshTokenRepository.findByUsernameAndHashedRefreshToken(user.username, hashedRefreshToken = hashedToken)
            ?: throw IllegalArgumentException("Invalid refresh token")
        refreshTokenRepository.deleteByUsernameAndHashedRefreshToken(
            username = user.username,
            hashedRefreshToken = hashedToken
        )
        val newAccessToken = jwtService.generateAccessToken(user.username)
        val newRefreshToken = jwtService.generateRefreshToken(user.username)
        saveRefreshToken(user.username, rawRefreshToken = newRefreshToken)
        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    override fun saveRefreshToken(username: String, rawRefreshToken: String) {
        val token = hashRefreshToken(rawRefreshToken)
        val expireAt = Instant.now().plus(7, ChronoUnit.DAYS)
        refreshTokenRepository.save(
            RefreshToken(
                username = username,
                hashedRefreshToken = token,
                expireAt = expireAt
            )
        )
    }

    @Transactional
    override fun logout(request: RefreshTokenRequest): String {
        val rawToken = request.refreshToken
        if (!jwtService.validateRefreshToken(rawToken))
            throw IllegalArgumentException("Invalid refresh token")
        val username = jwtService.extractUsername(token = rawToken)
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User doesn't exist")
        val hashedToken = hashRefreshToken(request.refreshToken)
        refreshTokenRepository.deleteByUsernameAndHashedRefreshToken(
            username = user.username,
            hashedRefreshToken = hashedToken
        )
        return "Success"
    }

    @Transactional
    override fun changePassword(
        changePasswordRequest: ChangePasswordRequest
    ): RegisterResponse {
        val username = changePasswordRequest.username
        val token = changePasswordRequest.refreshToken
        val currentPassword = changePasswordRequest.currentPassword
        val newPassword = changePasswordRequest.newPassword
        if (!jwtService.validateRefreshToken(token = token))
            throw IllegalArgumentException("Invalid refresh token")
        val user = userRepository.findByUsername(username = username)
            ?: throw IllegalArgumentException("User doesn't exist")
        val hashedCurrentPassword = passwordEncoder.encode(currentPassword)
        if (!passwordEncoder.matches(
                currentPassword,
                hashedCurrentPassword
            )
        ) throw InvalidCredentialsException("Invalid credentials")
        val hashedPassword = passwordEncoder.encode(newPassword)
        user.hashedPassword = hashedPassword
        userRepository.save(user)
        return RegisterResponse(statusCode = HttpStatus.OK.value().toString(), status = "Success")
    }


}


fun hashRefreshToken(rawRefreshToken: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val bytes = messageDigest.digest(rawRefreshToken.toByteArray())
    return Encoders.BASE64.encode(bytes)
}
