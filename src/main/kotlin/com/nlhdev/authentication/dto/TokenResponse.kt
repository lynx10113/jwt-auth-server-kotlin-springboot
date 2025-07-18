package com.nlhdev.authentication.dto

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

data class RefreshTokenRequest(
    @field:NotBlank(message = "Invalid refresh token")
    val refreshToken: String
)

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)
