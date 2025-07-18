package com.nlhdev.authentication.dto

data class ChangePasswordRequest(
    val username: String,
    val currentPassword: String,
    val newPassword: String,
    val refreshToken: String
)
