package com.github.thorben.webnd.usecase

import com.github.thorben.webnd.domain.user.EmailAddress
import com.github.thorben.webnd.domain.user.User
import com.github.thorben.webnd.domain.user.Username
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Int> {
	fun findByEmailAddress(emailAddress: EmailAddress): Optional<User>
	fun findByEmailAddressOrUsername(emailAddress: EmailAddress, username: Username): Optional<User>
	fun findByUsername(username: Username): Optional<User>
	fun existsByEmailAddress(emailAddress: EmailAddress): Boolean
	fun existsByUsername(username: Username): Boolean
}