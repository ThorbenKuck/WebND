package com.github.thorben.webnd.foundation

import com.github.thorben.webnd.domain.character.Attribute
import com.github.thorben.webnd.domain.character.BaseValue
import com.github.thorben.webnd.domain.character.Skill
import java.util.*
import java.util.function.Consumer

class MathematicalEvaluator : AbstractEvaluator() {
	fun evaluateAttributes(attributeList: List<Attribute>): Map<String, String> {
		val result = HashMap<String, String>()
		attributeList.map {
			doEvaluate(
					name = it.type!!.name,
					rawExpression = null,
					value = it.value!!,
					evalType = "attribute"
			)
		}.forEach { result[it.key] = it.value }
		return result
	}

	fun evaluate(baseValueTypes: List<BaseValue>): Map<String, String> {
		val result = HashMap<String, String>()
		baseValueTypes.map {
			doEvaluate(
				name = it.type!!.name,
				rawExpression = it.type!!.expression,
				value = it.value!!, evalType = "base"
			)
		}.forEach { result[it.key] = it.value }
		return result
	}

	fun evaluateSkills(skills: List<Skill>): Map<String, String> {
		val result = HashMap<String, String>()
		skills.map {
			doEvaluate(
					name = it.type!!.name,
					rawExpression = it.type!!.expression,
					value = it.value!!,
					evalType = "skill"
			)
		}.forEach { result[it.key] = it.value }
		return result
	}
}