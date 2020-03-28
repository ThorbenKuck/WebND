package com.github.thorben.webnd.application

import com.github.thorben.webnd.application.dto.CreationDto
import com.github.thorben.webnd.domain.character.*
import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.domain.games.GameTypeRepository
import com.github.thorben.webnd.domain.user.User
import com.github.thorben.webnd.domain.user.UserRole
import com.github.thorben.webnd.foundation.MathematicalCharacterCreationEvaluator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Component
@SessionScope
class SessionStorage(
		private val skillTypeRepository: SkillTypeRepository,
		private val attributeTypeRepository: AttributeTypeRepository,
		private val detailTypeRepository: DetailTypeRepository,
		private val baseValueTypeRepository: BaseValueTypeRepository,
		private val gameTypeRepository: GameTypeRepository
) {
	val user: Optional<User>
		get() = Optional.ofNullable(_user)

	private var _user: User? = null
	private var currentCharacter: Character? = null
	private var evaluator: MathematicalCharacterCreationEvaluator = MathematicalCharacterCreationEvaluator()
	fun identify(user: User?) {
		this._user = user
	}

	fun verifyAuthentication(other: () -> ResponseEntity<Any>): ResponseEntity<*> {
		return if (!isLoggedIn) {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
		} else try {
			other()
		} catch (throwable: Throwable) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable)
		}
	}

	private fun matchesToUser(inlined: (user: User) -> Boolean): Boolean {
		val temp = _user
		return temp != null && inlined(temp)
	}

	private fun modifyCharacter(inlined: (character: Character) -> Unit) {
		checkNotNull(currentCharacter) { "Du befindest dich grade nicht in der Charakter erstellung!" }
		inlined(currentCharacter!!)
	}

	val isLoggedIn: Boolean
		get() = _user != null

	val isModerator: Boolean
		get() = matchesToUser { it.userRole === UserRole.MODERATOR }

	fun prettyPrint(): String {
		val temp = _user
		return if (temp == null) {
			"Nicht angemeldet"
		} else {
			temp.username.toString() + temp.userRole.pretty()
		}
	}

	fun invalidate() {
		_user = null
	}

	@Deprecated("")
	fun generateAttributeCreationDto(): List<CreationDto> {
		val temp = currentCharacter
		return if (temp == null) {
			emptyList()
		} else {
			attributeTypeRepository.findAllByGameType(temp.gameType)
					.stream()
					.map { CreationDto(it.name, it.description, it.id!!, "", it.isRequired) }
					.collect(Collectors.toList())
		}
	}

	@Deprecated("")
	fun prepareCharacterCreation(name: String, gameType: GameType) {
		val newCharacter = Character()
		newCharacter.name = name
		newCharacter.gameType = gameType
		currentCharacter = newCharacter
	}

	@Deprecated("")
	fun characterCreationSetAttributes(attributes: List<Attribute>) {
		modifyCharacter {
			evaluator.storeAttributes(attributes)
			it.setAttributes(attributes)
		}
	}

	@Deprecated("")
	fun characterCreationSetBaseValues(baseValues: List<BaseValue>) {
		modifyCharacter {
			evaluator.storeBaseValues(baseValues)
			it.setBaseValues(baseValues)
		}
	}

	@Deprecated("")
	fun characterCreationSetDetails(details: List<Detail>) {
		modifyCharacter {
			it.setDetails(details)
		}
	}

	@Deprecated("")
	fun characterCreationSetSkills(skills: List<Skill>) {
		modifyCharacter {
			evaluator.storeSkills(skills)
			it.setSkills(skills)
		}
	}

	@Deprecated("")
	fun loadSkillTypes(): List<SkillType> {
		val types: MutableList<SkillType> = ArrayList()
		skillTypeRepository.findAll().forEach(Consumer { e: SkillType -> types.add(e) })
		return types
	}

	@get:Deprecated("")
	val skillCreationDtoList: List<CreationDto>
		get() = evaluator.createSkillCreations(loadSkillTypes())

	@get:Deprecated("")
	val baseValueCreationDtoList: List<CreationDto>
		get() = evaluator.generateBaseValueCreations(loadBaseValueTypes())

	@Deprecated("")
	fun loadAttributeTypes(): List<AttributeType> {
		val types: MutableList<AttributeType> = ArrayList()
		attributeTypeRepository.findAll().forEach { types.add(it) }
		return types
	}

	@Deprecated("")
	fun loadDetailTypes(): List<DetailType> {
		val types: MutableList<DetailType> = ArrayList()
		detailTypeRepository.findAll().forEach { types.add(it) }
		return types
	}

	@Deprecated("")
	fun loadBaseValueTypes(): List<BaseValueType> {
		val types: MutableList<BaseValueType> = ArrayList()
		baseValueTypeRepository.findAll().forEach { types.add(it) }
		return types
	}

	@Deprecated("")
	fun loadGameTypes(): List<GameType> {
		val types: MutableList<GameType> = ArrayList()
		gameTypeRepository.findAll().forEach { types.add(it) }
		return types
	}
}