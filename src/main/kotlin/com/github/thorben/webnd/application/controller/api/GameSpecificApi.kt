package com.github.thorben.webnd.application.controller.api

import com.github.thorben.webnd.application.SessionStorage
import com.github.thorben.webnd.application.controller.api.dto.SkillTypeList
import com.github.thorben.webnd.application.dto.CreationDto
import com.github.thorben.webnd.domain.character.*
import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.domain.games.GameTypeRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.function.Consumer

@RestController
class GameSpecificApi(
		private val skillTypeRepository: SkillTypeRepository,
		private val detailTypeRepository: DetailTypeRepository,
		private val attributeTypeRepository: AttributeTypeRepository,
		private val gameTypeRepository: GameTypeRepository,
		private val baseValueRepository: BaseValueTypeRepository,
		private val sessionStorage: SessionStorage
) {


	@GetMapping(value = ["/api/game_types"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun allGameTypes(): ResponseEntity<List<GameType>> {
		val result: MutableList<GameType> = ArrayList()
		gameTypeRepository.findAll().forEach(Consumer { e: GameType -> result.add(e) })
		return ResponseEntity.ok(result)
	}

	@GetMapping(value = ["/api/game_types/{gameId}/skills"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun getAllSkills(@PathVariable gameId: Int): ResponseEntity<List<SkillType>> {
		val byId = gameTypeRepository.findById(gameId)
		return if (!byId.isPresent) {
			ResponseEntity.ok(emptyList())
		} else {
			ResponseEntity.ok(skillTypeRepository.findAllByGameType(byId.get()))
		}
	}

	@PostMapping("/api/characterCreation/{gameId}")
	fun prepareCharacterCreation(@PathVariable id: Int, characterName: String): ResponseEntity<*> {
		val byId = gameTypeRepository.findById(id)
		if (!byId.isPresent) {
			return ResponseEntity.notFound().build<Any>()
		}
		sessionStorage.prepareCharacterCreation(characterName, byId.get())
		return ResponseEntity.accepted().build<Any>()
	}

	@get:GetMapping(value = ["/api/game_types/{gameId}/skillCreation"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	val allSkillCreations: ResponseEntity<List<CreationDto>>
		get() = ResponseEntity.ok(sessionStorage.skillCreationDtoList)

	@get:GetMapping(value = ["/api/game_types/{gameId}/baseValueCreation"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	val allBaseValueCreations: ResponseEntity<List<CreationDto>>
		get() = ResponseEntity.ok(sessionStorage.baseValueCreationDtoList)

	@GetMapping(value = ["/api/game_types/{gameId}/abilities"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun getAllAttributes(@PathVariable gameId: Int): ResponseEntity<List<AttributeType?>?> {
		val byId = gameTypeRepository.findById(gameId)
		return if (!byId.isPresent) {
			ResponseEntity.ok(emptyList())
		} else ResponseEntity.ok(attributeTypeRepository.findAllByGameType(byId.get()))
	}

	@GetMapping(value = ["/api/game_types/{gameId}/baseValues"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun getAllBaseValues(@PathVariable gameId: Int): ResponseEntity<List<BaseValueType>> {
		val byId = gameTypeRepository.findById(gameId)
		return if (!byId.isPresent) {
			ResponseEntity.ok(emptyList())
		} else {
			ResponseEntity.ok(baseValueRepository.findAllByGameType(byId.get()))
		}
	}

	@GetMapping(value = ["/api/game_types/{gameId}/details"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun getAllDetails(@PathVariable gameId: Int): ResponseEntity<List<DetailType>> {
		val byId = gameTypeRepository.findById(gameId)
		return if (!byId.isPresent) {
			ResponseEntity.ok(emptyList())
		} else {ResponseEntity.ok(detailTypeRepository.findAllByGameType(byId.get()))}
	}

	@PostMapping(value = ["/api/game_types/{gameId}/skills"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun newSkillType(name: String, description: String, expression: String, skillRequired: Boolean, @PathVariable gameId: Int): ResponseEntity<*> {
		val gameType = gameTypeRepository.findById(gameId)
		if (!gameType.isPresent) {
			return ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
		}
		if (skillTypeRepository.findByName(name).isPresent) {
			return ResponseEntity<Any?>(HttpStatus.CONFLICT)
		}
		val skillType = SkillType()
		skillType.isRequired = skillRequired
		skillType.gameType = gameType.get()
		skillType.name = name
		skillType.expression = expression
		skillType.description = description
		skillTypeRepository.save(skillType)
		val skillTypes: MutableList<SkillType> = ArrayList()
		skillTypeRepository.findAll().forEach(Consumer { e: SkillType -> skillTypes.add(e) })
		return ResponseEntity.accepted().body(SkillTypeList(skillTypes))
	}

	@PostMapping(value = ["/api/game_types/{gameId}/abilities"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun newAttributeType(name: String, description: String, abilityRequired: Boolean, @PathVariable gameId: Int): ResponseEntity<*> {
		val gameType = gameTypeRepository.findById(gameId)
		if (!gameType.isPresent) {
			return ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
		}
		val attributeType = AttributeType()
		attributeType.gameType = gameType.get()
		attributeType.isRequired = abilityRequired
		attributeType.name = name
		attributeType.description = description
		attributeTypeRepository.save(attributeType)
		val abilityTypes: MutableList<AttributeType> = ArrayList()
		attributeTypeRepository.findAll().forEach { abilityTypes.add(it) }
		return ResponseEntity.ok<List<AttributeType>>(abilityTypes)
	}

	@PostMapping(value = ["/api/game_types/{gameId}/details"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun newCharacterDetail(name: String, description: String, detailRequired: Boolean, @PathVariable gameId: Int): ResponseEntity<*> {
		val gameType = gameTypeRepository.findById(gameId)
		if (!gameType.isPresent) {
			return ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
		}
		val detailType = DetailType(gameType.get(), name, description)
		detailType.isRequired = detailRequired
		detailTypeRepository.save(detailType)
		return ResponseEntity<Any?>(HttpStatus.OK)
	}

	@PostMapping(value = ["/api/game_types/{gameId}/baseValues"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
	fun newBaseValue(name: String, description: String, expression: String, baseValueRequired: Boolean, @PathVariable gameId: Int): ResponseEntity<*> {
		val gameType = gameTypeRepository.findById(gameId)
		if (!gameType.isPresent) {
			return ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
		}
		val baseValueType = BaseValueType()
		baseValueType.gameType = gameType.get()
		baseValueType.isRequired = baseValueRequired
		baseValueType.name = name
		baseValueType.description = description
		baseValueType.expression = expression
		baseValueRepository.save(baseValueType)
		return ResponseEntity<Any?>(HttpStatus.OK)
	}
}