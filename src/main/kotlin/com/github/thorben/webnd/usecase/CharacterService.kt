package com.github.thorben.webnd.usecase

import com.github.thorben.webnd.foundation.MathematicalEvaluator

class CharacterService {
	fun generate(): CharacterSheetDto {
		val dto = CharacterSheetDto()
		val mathematicalEvaluator = MathematicalEvaluator()
		return dto
	}
}