package com.nlhdev.authentication.exception

class InvalidCredentialsException(message: String = "Invalid credentials") : IllegalArgumentException(message)

class UsernameAlreadyExistsException(message: String = "User already exists") : IllegalArgumentException(message)