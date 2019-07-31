package com.github.thorben.webnd.application.controller.api;

import com.github.thorben.webnd.application.SessionStorage;
import com.github.thorben.webnd.domain.character.Character;
import com.github.thorben.webnd.usecase.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api")
public class UserApi {

	@Inject
	private SessionStorage sessionStorage;
	@Inject
	private UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity getAll() {
		return sessionStorage.verifyAuthentication(() -> ResponseEntity.ok(userRepository.findAll()));
	}

	@GetMapping("/user/characters")
	public ResponseEntity getCharacters() {
		return sessionStorage.verifyAuthentication(() -> {
			AtomicReference<List<Character>> atomicReference = new AtomicReference<>(new ArrayList<>());
			sessionStorage.getUser().ifPresent(user -> atomicReference.set(user.getCharacters()));
			return ResponseEntity.ok(atomicReference.get());
		});
	}

}
