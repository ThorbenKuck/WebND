package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType

interface Type {
	val id: Int?
	var name: String
	var description: String
	var expression: String
	var gameType: GameType
	var isRequired: Boolean
}