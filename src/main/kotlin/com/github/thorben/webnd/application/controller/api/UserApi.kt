package com.github.thorben.webnd.application.controller.api

import com.github.thorben.webnd.application.SessionStorage
import com.github.thorben.webnd.domain.character.Character
import com.github.thorben.webnd.usecase.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.atomic.AtomicReference

@RestController
@RequestMapping("/api")
class UserApi(
		private val sessionStorage: SessionStorage,
		private val userRepository: UserRepository
) {


	@GetMapping("/users")
	fun all(): ResponseEntity<*> = sessionStorage.verifyAuthentication { ResponseEntity.ok(userRepository.findAll()) }

	@GetMapping("/user/characters")
	fun characters(): ResponseEntity<*> = sessionStorage.verifyAuthentication {
			val atomicReference = AtomicReference<List<Character>>(ArrayList())
			sessionStorage.user.ifPresent { atomicReference.set(it.characters) }
			ResponseEntity.ok(atomicReference.get())
	}
}