package com.nlhdev.authentication.service

import com.nlhdev.authentication.dto.AuthRequest
import com.nlhdev.authentication.dto.AuthResponse
import com.nlhdev.authentication.dto.ChangePasswordRequest
import com.nlhdev.authentication.dto.RefreshTokenRequest
import com.nlhdev.authentication.dto.RegisterRequest
import com.nlhdev.authentication.dto.RegisterResponse
import com.nlhdev.authentication.dto.TokenPair
import com.nlhdev.authentication.dto.UserDetail

interface AuthService {

    fun register(request: RegisterRequest): RegisterResponse

    fun authenticate(request: AuthRequest): AuthResponse

    fun getUserInfo(username: String): UserDetail?

    fun refreshToken(rawRefreshToken: RefreshTokenRequest): TokenPair

    fun saveRefreshToken(username: String, rawRefreshToken: String)

    fun logout(request: RefreshTokenRequest): String

    fun changePassword(changePasswordRequest: ChangePasswordRequest): RegisterResponse

}