package com.nlhdev.authentication.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.security.MessageDigest

class AuthPasswordEncoder: PasswordEncoder {
    val bcrypt: PasswordEncoder = BCryptPasswordEncoder()

    override fun encode(rawPassword: CharSequence?): String? {
        val hashedPassword = toSha256Hex(input = rawPassword.toString())
        return bcrypt.encode(hashedPassword)
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        val hashedPassword = toSha256Hex(rawPassword.toString())
        return bcrypt.matches(hashedPassword, encodedPassword)
    }

    fun toSha256Hex(input: String): String{
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hash = messageDigest.digest(input.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}