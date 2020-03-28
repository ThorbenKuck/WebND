package com.github.thorben.webnd.application.dto

data class CreationDto(
		val name: String,
		val description: String,
		val typeId: Int,
		val preDeterminedValue: String,
		val isRequired: Boolean
)