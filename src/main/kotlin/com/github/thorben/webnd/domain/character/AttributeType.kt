package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "AttributeTypes")
@Table(name = "attribute_types")
class AttributeType : AbstractEntity<Int?>() {
	@Id
	@GeneratedValue
	override val id: Int? = null

	@ManyToOne
	lateinit var gameType: GameType

	@Column(name = "attribute_type_name")
	lateinit var name: String

	@Column(name = "attribute_type_description")
	lateinit var description: String

	@Column(name = "attribute_type_required")
	var isRequired = false

}