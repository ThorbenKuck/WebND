package com.github.thorben.webnd.application.dto

import com.github.thorben.webnd.domain.character.Attribute
import com.github.thorben.webnd.domain.character.AttributeType
import com.github.thorben.webnd.domain.character.Type
import com.github.thorben.webnd.foundation.MathematicalEvaluator
import java.util.stream.Collectors

class CreationFactory {
	private val mathematicalEvaluator: MathematicalEvaluator? = null
	fun store(attributes: List<Attribute>) {
		mathematicalEvaluator!!.evaluateAttributes(attributes)
	}

	fun map(attributeTypes: List<AttributeType>): List<CreationDto> {
		return attributeTypes.stream()
				.map {
					CreationDto(it.name,
							it.description,
							it.id!!,
							"",
							it.isRequired)
				}
				.collect(Collectors.toList())
	}

	fun mapAnyType(attributeTypes: List<Type>): List<CreationDto> {
		return attributeTypes.stream()
				.map {
					CreationDto(it.name,
							it.description,
							it.id!!,
							"",
							it.isRequired)
				}
				.collect(Collectors.toList())
	}
}