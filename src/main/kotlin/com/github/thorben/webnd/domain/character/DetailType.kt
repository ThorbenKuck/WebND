package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "DetailsType")
@Table(name = "detail_types")
class DetailType : AbstractEntity<Int?> {
	@Id
	@GeneratedValue
	override val id: Int? = null

	@ManyToOne
	var gameType: GameType? = null
		private set

	@Column(name = "detail_type_name", unique = true)
	var name: String? = null
		private set

	@Column(name = "detail_type_description")
	var description: String? = null
		private set

	@Column(name = "detail_type__required")
	var isRequired = false

	protected constructor() {
		// JPA
	}

	constructor(gameType: GameType?, name: String?, description: String?) {
		this.gameType = gameType
		this.name = name
		this.description = description
	}

}