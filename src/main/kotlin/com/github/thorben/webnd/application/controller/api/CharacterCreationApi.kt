package com.github.thorben.webnd.application.controller.api

import com.github.thorben.webnd.domain.character.SkillTypeRepository
import com.github.thorben.webnd.domain.games.GameTypeRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(name = "/api/game_types/{gameId}")
class CharacterCreationApi(
		private val skillTypeRepository: SkillTypeRepository,
		private val gameTypeRepository: GameTypeRepository
) {


	@GetMapping("/skill_types")
	fun getAllSkillTypes(@PathVariable gameId: Int): ResponseEntity<*> {
		val gameType = gameTypeRepository.findById(gameId)
		if (!gameType.isPresent) {
			return ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
		}
		val allByGameType = skillTypeRepository.findAllByGameType(gameType.get())
		return ResponseEntity<Any?>(allByGameType, HttpStatus.OK)
	}
}