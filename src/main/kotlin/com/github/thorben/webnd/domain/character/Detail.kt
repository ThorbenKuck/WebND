package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "Details")
@Table(name = "details")
class Detail protected constructor() : AbstractEntity<Int?>() {
	@Id
	@GeneratedValue
	override val id: Int? = null

	@JoinColumn(name = "id", table = "detail_types")
	private val type: DetailType? = null

	@Column(name = "detail_value")
	private val value: String? = null

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_id")
	private val character: Character? = null
}