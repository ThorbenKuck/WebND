package com.github.thorben.webnd.usecase

import com.github.thorben.webnd.domain.user.*
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject

@Service
class UserService(
		private val userRepository: UserRepository
) {
	fun emailExists(rawEmail: String): Boolean {
		return userRepository.findByEmailAddress(EmailAddress(rawEmail)).isPresent
	}

	fun createNewUser(email: String, password: String, username: String): User {
		val salt = BCrypt.gensalt()
		val hashedPassword = BCrypt.hashpw(password, salt)
		val user = User(EmailAddress(email), Password(hashedPassword), Username(username), UserRole.USER)
		userRepository.save(user)
		return user
	}

	fun login(id: String, password: String): Optional<User> {
		val userOptional = userRepository.findByEmailAddressOrUsername(EmailAddress(id), Username(id))
		if (!userOptional.isPresent) {
			return Optional.empty()
		}
		val user = userOptional.get()
		val storedPassword = user.password
		return if (BCrypt.checkpw(password, storedPassword.value)) {
			userOptional
		} else {
			Optional.empty()
		}
	}

	private var testCreated = false
	fun createTestUser() {
		if (!testCreated) {
			val user = User(EmailAddress("test@test.test"), Password(BCrypt.hashpw("test", BCrypt.gensalt())), Username("Test"), UserRole.MODERATOR)
			userRepository.save(user)
			testCreated = true
		}
	}
}