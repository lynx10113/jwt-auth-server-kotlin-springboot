package com.nlhdev.authentication.exception

import com.nlhdev.authentication.dto.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                statusCode = HttpStatus.BAD_REQUEST.value().toString(),
                error = "Bad Credentials",
                message = "Invalid username or password"
            )
        )
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwt(exception: ExpiredJwtException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorResponse(
                statusCode = HttpStatus.UNAUTHORIZED.value().toString(),
                error = "Unauthorized",
                message = exception.message ?: "Token expired"
            )
        )
    }

    @ExceptionHandler(MalformedJwtException::class, SignatureException::class)
    fun handleInvalidToken(): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorResponse(
                statusCode = HttpStatus.UNAUTHORIZED.value().toString(),
                error = "Unauthorized",
                message = "Invalid token"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalServerError(exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponse(
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
                error = "Unexpected Error",
                message = exception?.message ?: "Internal Server Error"
            )
        )
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentials(exception: InvalidCredentialsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                statusCode = HttpStatus.BAD_REQUEST.value().toString(),
                error = "Invalid Credentials",
                message = exception.message ?: "Invalid credentials"
            )
        )
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFound(exception: UsernameNotFoundException): ResponseEntity<ErrorResponse>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
                statusCode = HttpStatus.NOT_FOUND.value().toString(),
                error = "Not Found",
                message = exception?.message.toString()
            )
        )
    }

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameAlreadyExists(exception: UsernameAlreadyExistsException): ResponseEntity<ErrorResponse>{
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(
                statusCode = HttpStatus.CONFLICT.value().toString(),
                error = "Username Already Exists",
                message = exception?.message.toString()
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse>{
        val message =exception.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid arguments")
        }.values.firstOrNull()
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                statusCode = HttpStatus.BAD_REQUEST.value().toString(),
                error = "Invalid Arguments",
                message = message ?: "Invalid arguments"
            )
        )
    }


}