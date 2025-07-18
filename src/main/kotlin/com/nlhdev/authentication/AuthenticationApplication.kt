package com.nlhdev.authentication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class AuthenticationApplication

fun main(args: Array<String>) {
	runApplication<AuthenticationApplication>(*args)
}
