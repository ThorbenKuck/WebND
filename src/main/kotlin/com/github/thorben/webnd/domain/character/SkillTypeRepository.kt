package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SkillTypeRepository : CrudRepository<SkillType, Int> {
	fun findAllByGameType(gameType: GameType): List<SkillType>
	fun findByName(name: String): Optional<SkillType>
}