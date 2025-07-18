package com.nlhdev.authentication.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthRequest(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 6, max = 30, message = "Username must be at least 6 characters")
    val username: String,
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 30, message = "Password must be at least 6 characters")
    val password: String
)

data class AuthResponse(
    val statusCode: String,
    val status: String,
    val accessToken: String,
    val refreshToken: String
)
