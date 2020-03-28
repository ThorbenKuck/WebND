package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "BaseValueType")
@Table(name = "base_values_type")
class BaseValueType : AbstractEntity<Int?>(), Type {
	@Id
	@GeneratedValue
	override val id: Int? = null

	@ManyToOne
	override lateinit var gameType: GameType

	@Column(name = "base_value_type_name", nullable = false)
	override lateinit var name: String

	@Column(name = "base_value_type_expression")
	override lateinit var expression: String

	@Column(name = "base_value_type_description")
	override lateinit var description: String

	@Column(name = "base_value_required")
	override var isRequired = false

}