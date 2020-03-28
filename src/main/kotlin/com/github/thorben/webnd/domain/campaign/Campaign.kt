package com.github.thorben.webnd.domain.campaign

import com.github.thorben.webnd.domain.character.Character
import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.domain.user.User
import java.util.*
import kotlin.collections.ArrayList

class Campaign(
		val id: UUID = UUID.randomUUID(),
		val name: String
) {
	private val characters = ArrayList<Character>()
	private val participants = ArrayList<User>()
	private val gameType: GameType? = null
}