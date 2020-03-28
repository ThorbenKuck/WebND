package com.github.thorben.webnd.foundation

import com.github.thorben.webnd.application.dto.CreationDto
import com.github.thorben.webnd.domain.character.*
import java.util.*
import java.util.function.Consumer

class MathematicalCharacterCreationEvaluator : AbstractEvaluator() {
	fun storeAttributes(attributeList: List<Attribute>) {
		attributeList.forEach {
			try {
				val value = it.value!!.toDouble()
				cache("attribute", it.type!!.name, value)
			} catch (ignored: NumberFormatException) {
			}
		}
	}

	fun storeBaseValues(baseValues: List<BaseValue>) {
		baseValues.forEach {
			try {
				val value = it.value!!.toDouble()
				cache("base", it.type!!.name, value)
			} catch (ignored: NumberFormatException) {
			}
		}
	}

	fun storeSkills(baseValues: List<Skill>) {
		baseValues.forEach {
			try {
				val value = it.value!!.toDouble()
				cache("skill", it.type!!.name, value)
			} catch (ignored: NumberFormatException) {
			}
		}
	}

	fun generateBaseValueCreations(baseValueTypes: List<BaseValueType>): List<CreationDto> {
		val result: MutableList<CreationDto> = ArrayList()
		baseValueTypes.forEach {
			val attribute = doEvaluate(it.name, it.expression, "", "base")
			val creationDto = CreationDto(it.name, it.description, it.id!!, attribute.value, it.isRequired)
			result.add(creationDto)
		}
		return result
	}

	fun createSkillCreations(skills: List<SkillType>): List<CreationDto> {
		val result: MutableList<CreationDto> = ArrayList()
		skills.forEach {
			val attribute = doEvaluate(it.name, it.expression, "", "skill")
			val creationDto = CreationDto(it.name, it.description, it.id!!, attribute.value, it.isRequired)
			result.add(creationDto)
		}
		return result
	}
}