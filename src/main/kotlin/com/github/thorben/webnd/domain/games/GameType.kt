package com.github.thorben.webnd.domain.games

import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

@Entity(name = "GameType")
@Table(name = "game_types")
class GameType(
		@Column(name = "game_type_description")
		var description: String,

		@Column(name = "game_type_name")
		var name: String
) : AbstractEntity<Int?>() {
	@Id
	@GeneratedValue
	override val id: Int? = null

	private constructor (): this("", "") {
		// JPA
	}
}