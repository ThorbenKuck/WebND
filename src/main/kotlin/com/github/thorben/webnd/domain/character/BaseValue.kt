package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "BaseValue")
@Table(name = "base_value")
class BaseValue : AbstractEntity<Int?>, TypeFill {
	@Id
	@GeneratedValue
	override val id: Int? = null

	@JoinColumn(name = "id", table = "skill_types")
	override var type: SkillType? = null
		private set

	@Column(name = "skill_value")
	override var value: String? = null
		private set

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_id")
	private val character: Character? = null

	protected constructor() {
		// JPA
	}

	constructor(type: SkillType?, value: String?) {
		this.type = type
		this.value = value
	}

}