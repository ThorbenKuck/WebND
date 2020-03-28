package com.github.thorben.webnd.foundation

import com.udojava.evalex.Expression
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

open class AbstractEvaluator {
	private val variables: MutableMap<String, BigDecimal> = HashMap()

	fun cache(prefix: String, name: String, value: Double) {
		variables[prefix + "_" + name.toLowerCase()] = BigDecimal.valueOf(value)
	}

	fun clear() {
		variables.clear()
	}

	fun doEvaluate(name: String, rawExpression: String?, value: String, evalType: String): EvaluationResult {
		var tempExpression = rawExpression
		return if (tempExpression == null) {
			try {
				val entry = value.toDouble()
				cache(evalType, name, entry)
				EvaluationResult(name, entry.toString())
			} catch (e: NumberFormatException) {
				// This special case is, that something not a number is set.
				// This might be (for example): Speed="30 ft."
				EvaluationResult(name, value)
			}
		} else {
			try {
				val valueDouble = value.toDouble()
				tempExpression = "($tempExpression) + $valueDouble"
			} catch (ignored: NumberFormatException) {
			}
			val expression = getExpression(tempExpression!!)
			val eval = expression.eval()
			val entry: Double = eval.toDouble()
			cache(evalType, name, entry)
			EvaluationResult(name, entry.toString())
		}
	}

	data class EvaluationResult(
			val key: String,
			val value: String
	)

	private fun getExpression(raw: String): Expression {
		val expression = Expression(raw).setRoundingMode(RoundingMode.HALF_DOWN)
		variables.forEach { expression.with(it.key, it.value) }
		return expression
	}
}