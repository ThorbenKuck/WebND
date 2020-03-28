package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "Attribute")
@Table(name = "attributes")
class Attribute : AbstractEntity<Int?> {
	@Id
	@GeneratedValue
	override val id: Int? = null

	@MapsId
	@JoinColumn(name = "id")
	var type: AttributeType? = null
		private set

	@Column(name = "attribute_value")
	var value: String? = null
		private set

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_id")
	private val character: Character? = null

	protected constructor() {
		// JPA
	}

	constructor(type: AttributeType?, value: String?) {
		this.type = type
		this.value = value
	}

}