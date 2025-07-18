package com.nlhdev.authentication.dto

data class ErrorResponse(
    val statusCode: String,
    val error: String,
    val message: String
)
