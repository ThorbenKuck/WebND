package com.github.thorben.webnd.application.controller.api.dto

data class RegistrationDto(
		val username: String,
		val email: String,
		val password: String
)