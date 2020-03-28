package com.github.thorben.webnd.application.controller.api

import com.github.thorben.webnd.application.SessionStorage
import com.github.thorben.webnd.application.controller.api.dto.RegistrationDto
import com.github.thorben.webnd.application.controller.api.dto.SimpleMessages
import com.github.thorben.webnd.domain.user.EmailAddress
import com.github.thorben.webnd.domain.user.Username
import com.github.thorben.webnd.usecase.UserRepository
import com.github.thorben.webnd.usecase.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthenticationApi(
		private val sessionStorage: SessionStorage,
		private val userRepository: UserRepository,
		private val userService: UserService
) {

	private fun constructErrors(email: String, username: String): SimpleMessages? {
		val simpleMessages = SimpleMessages()
		if (userRepository.existsByEmailAddress(EmailAddress(email))) {
			simpleMessages.addMessage("Die Email ist bereits vergeben")
		}
		if (userRepository.existsByUsername(Username(username))) {
			simpleMessages.addMessage("Die Email ist bereits vergeben")
		}
		return if (!simpleMessages.filled()) {
			null
		} else {simpleMessages}
	}

	@GetMapping("/authenticated")
	fun isAuthenticated(): ResponseEntity<Any>  {
		return if (sessionStorage.isLoggedIn) {
			ResponseEntity.ok().build()
		} else {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
		}
	}

	@PostMapping("/users")
	fun register(registrationDto: RegistrationDto): ResponseEntity<SimpleMessages> {
		val message = constructErrors(registrationDto.email, registrationDto.username)
		if (message != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(message)
		}
		val newUser = userService.createNewUser(registrationDto.email, registrationDto.password, registrationDto.username)
		sessionStorage.identify(newUser)
		return ResponseEntity.accepted().body(SimpleMessages("Okay"))
	}

	@PostMapping("/users/{id}/authenticated")
	fun login(@PathVariable id: String, password: String): ResponseEntity<Any> {
		if (sessionStorage.isLoggedIn) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build()
		}
		val login = userService.login(id, password)
		return if (login.isPresent) {
			ResponseEntity.accepted().build()
		} else {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build()
		}
	}

	@DeleteMapping("/authenticated")
	fun logout(): ResponseEntity<Any> {
		if (!sessionStorage.isLoggedIn) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build()
		}
		sessionStorage.invalidate()
		return ResponseEntity.accepted().build()
	}
}