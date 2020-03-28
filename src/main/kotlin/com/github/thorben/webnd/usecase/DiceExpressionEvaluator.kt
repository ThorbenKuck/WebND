package com.github.thorben.webnd.usecase

import com.udojava.evalex.Expression
import org.springframework.stereotype.Component
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList

data class Dice(
		val sides: Int,
		val amount: Int = 1,
		val additional: String? = null
)

data class TotalDiceResult(
		val dices: ArrayList<DiceResult> = ArrayList(),
		var result: Int = 0
) {
	operator fun plusAssign(int: Int) {
		result += int
	}

	operator fun plusAssign(diceResult: DiceResult) {
		dices.add(diceResult)
	}
}

data class Roll(
		val result: Int
)

data class DiceResult(
		val dice: Dice,
		val rolls: List<Roll>,
		val withoutExpression: Int,
		val withExpression: Int
)

@Component
class DiceExpressionEvaluator {

	private val random = Random()

	private fun roll(sides: Int): Int {
		return synchronized(random) {
			random.nextInt(sides) + 1
		}
	}

	fun evaluate(dices: Collection<Dice>): TotalDiceResult {
		val diceResult = TotalDiceResult()
		dices.forEach {
			var withoutExpression = 0
			val rolls = ArrayList<Roll>()
			if (it.amount > 1) {
				for (i in 1..it.amount) {
					val roll = roll(it.sides)
					withoutExpression += roll
					rolls.add(Roll(roll))
				}
			} else {
				val roll = roll(it.sides)
				withoutExpression = roll
				rolls.add(Roll(roll))
			}

			if(it.additional != null) {
				val withExpression = withoutExpression + Expression("$withoutExpression ${it.additional}").setRoundingMode(RoundingMode.HALF_DOWN)
						.eval()
						.toInt()
				diceResult += DiceResult(it, rolls, withoutExpression, withExpression)
				diceResult += withExpression
			} else {
				diceResult += DiceResult(it, rolls, withoutExpression, withoutExpression)
				diceResult += withoutExpression
			}
		}

		return diceResult
	}
}