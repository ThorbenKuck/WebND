package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "SkillType")
@Table(name = "skill_types")
class SkillType : AbstractEntity<Int?>(), Type {
	@Id
	@GeneratedValue
	override val id: Int? = null

	@ManyToOne
	override lateinit var gameType: GameType

	@Column(name = "skill_type_name", unique = true)
	override lateinit var name: String

	@Column(name = "skill_type_description")
	override lateinit var description: String

	@Column(name = "skill_type_expression")
	override lateinit var expression: String

	@Column(name = "skill_type_required")
	override var isRequired = false

}