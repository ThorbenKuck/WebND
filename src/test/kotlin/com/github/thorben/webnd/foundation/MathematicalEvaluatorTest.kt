package com.github.thorben.webnd.foundation

import com.github.thorben.webnd.domain.character.Attribute
import com.github.thorben.webnd.domain.character.AttributeType
import com.github.thorben.webnd.domain.character.Skill
import com.github.thorben.webnd.domain.character.SkillType
import com.github.thorben.webnd.domain.games.GameType
import org.junit.jupiter.api.Test

class MathematicalEvaluatorTest {
	@Test
	fun test() {
		val mathematicalEvaluator = MathematicalEvaluator()
		val gameType = GameType("blabla", "DnD 5e")

		val attributeType = AttributeType()
		val skillType = SkillType()

		attributeType.gameType = gameType
		attributeType.name = "Strength"

		skillType.gameType = gameType
		skillType.name = "Acrobatics"
		skillType.expression = "FLOOR((attribute_strength - 10) / 2)"

		val attributes = listOf(Attribute(attributeType, "15"))
		val attributeMap = mathematicalEvaluator.evaluateAttributes(attributes)
		val skills = listOf(Skill(skillType, ""))
		val skillMap = mathematicalEvaluator.evaluateSkills(skills)

		println(attributeMap)
		println(skillMap)
	}
}