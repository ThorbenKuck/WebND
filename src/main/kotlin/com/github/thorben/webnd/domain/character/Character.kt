package com.github.thorben.webnd.domain.character

import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.domain.user.User
import com.github.thorben.webnd.foundation.AbstractEntity
import javax.persistence.*

/**
 * Attributes are not evaluated. Those are (for example) something like Strength.
 * BaseValues are directly inherited from Attributes. This is (for example) Life/Mana and so on.
 * Skills are abilities of characters, which are directly influenced from Attributes. This is (for example) Perception
 * Details cannot be evaluated. This is something like Age and Height.
 */
@Entity(name = "Character")
@Table(name = "character")
class Character(
) : AbstractEntity<Int?>() {

	@Id
	@GeneratedValue
	override var id: Int? = null

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	var user: User? = null

	@OneToOne
	@MapsId
	lateinit var gameType: GameType

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	val attributes: MutableList<Attribute> = ArrayList()

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	val skills: MutableList<Skill> = ArrayList()

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	val details: MutableList<Detail> = ArrayList()

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	val baseValues: MutableList<BaseValue> = ArrayList()

	@Column(name = "character_name", nullable = false)
	lateinit var name: String

	fun setAttributes(list: Collection<Attribute>) {
		attributes.clear()
		attributes.addAll(list)
	}

	fun setSkills(list: Collection<Skill>) {
		skills.clear()
		skills.addAll(list)
	}

	fun setDetails(list: Collection<Detail>) {
		details.clear()
		details.addAll(list)
	}

	fun setBaseValues(list: Collection<BaseValue>) {
		baseValues.clear()
		baseValues.addAll(list)
	}

}