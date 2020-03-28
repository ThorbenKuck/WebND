package com.github.thorben.webnd.domain.user

import com.github.thorben.webnd.domain.character.Character
import com.github.thorben.webnd.foundation.AbstractEntity
import org.thymeleaf.util.Validate
import javax.persistence.*

@Entity(name = "User")
@Table(name = "user")
open class User(
		@Embedded
		@AttributeOverride(name = "value", column = Column(name = "user_email", unique = true, nullable = false))
		val emailAddress: EmailAddress,

		@Embedded
		@AttributeOverride(name = "value", column = Column(name = "user_password", nullable = false))
		val password: Password,

		@Embedded
		@AttributeOverride(name = "value", column = Column(name = "user_name", unique = true, nullable = false))
		val username: Username,

		@Column(name = "user_role", nullable = false)
		@Enumerated(EnumType.STRING)
		val userRole: UserRole = UserRole.USER
) : AbstractEntity<Int?>() {
	@OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
	val characters: MutableList<Character> = ArrayList()

	@Id
	@GeneratedValue
	override val id: Int? = null

	@Embedded
	@AttributeOverride(name = "value", column = Column(name = "user_surname"))
	var surname: Surname? = null

	@Embedded
	@AttributeOverride(name = "value", column = Column(name = "user_firstName"))
	var firstName: FirstName? = null

	protected constructor(): this(EmailAddress(""), Password(""), Username("")) {
		// JPA
	}

	fun addCharacter(character: Character) {
		characters.add(character)
	}

}