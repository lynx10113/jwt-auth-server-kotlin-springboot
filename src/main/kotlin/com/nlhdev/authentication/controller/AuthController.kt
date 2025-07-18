package com.nlhdev.authentication.controller

import com.nlhdev.authentication.dto.AuthRequest
import com.nlhdev.authentication.dto.AuthResponse
import com.nlhdev.authentication.dto.ChangePasswordRequest
import com.nlhdev.authentication.dto.RefreshTokenRequest
import com.nlhdev.authentication.dto.RegisterRequest
import com.nlhdev.authentication.dto.RegisterResponse
import com.nlhdev.authentication.dto.TokenPair
import com.nlhdev.authentication.dto.UserDetail
import com.nlhdev.authentication.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(private val service: AuthService) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse>{
        val registerBody = service.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(registerBody)
    }

    @PostMapping("/authenticate")
    fun authenticate(@Valid @RequestBody request: AuthRequest): ResponseEntity<AuthResponse>{
        val authBody = service.authenticate(request)
        return ResponseEntity.status(HttpStatus.OK).body(authBody)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<TokenPair>{
        val tokenPair = service.refreshToken(request)
        return ResponseEntity.status(HttpStatus.OK).body(tokenPair)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: RefreshTokenRequest): ResponseEntity<String>{
        val logout = service.logout(request)
        return ResponseEntity.status(HttpStatus.OK).body("Success")
    }

    @PostMapping("/change-password")
    fun changePassword(@RequestBody request: ChangePasswordRequest): ResponseEntity<RegisterResponse>{
        val changePassword = service.changePassword(request)
        return ResponseEntity.status(HttpStatus.OK).body(changePassword)
    }

    @GetMapping("users/{username}")
    fun getUserInfo(@PathVariable username: String): ResponseEntity<UserDetail>{
        val user = service.getUserInfo(username = username)
        return ResponseEntity.ok(user)
    }
}