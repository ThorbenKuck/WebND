package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AttributeTypeRepository : CrudRepository<AttributeType, Int> {
	fun findAllByGameType(gameType: GameType): List<AttributeType>
}