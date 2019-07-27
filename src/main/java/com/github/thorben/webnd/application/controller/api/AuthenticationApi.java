package com.github.thorben.webnd.application.controller.api;

import com.github.thorben.webnd.application.SessionStorage;
import com.github.thorben.webnd.application.controller.api.dto.RegistrationDto;
import com.github.thorben.webnd.application.controller.api.dto.SimpleMessages;
import com.github.thorben.webnd.domain.user.EmailAddress;
import com.github.thorben.webnd.domain.user.User;
import com.github.thorben.webnd.domain.user.Username;
import com.github.thorben.webnd.usecase.UserRepository;
import com.github.thorben.webnd.usecase.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticationApi {

	@Inject
	private SessionStorage sessionStorage;
	@Inject
	private UserRepository userRepository;
	@Inject
	private UserService userService;

	private SimpleMessages constructErrors(String email, String username) {
		SimpleMessages simpleMessages = new SimpleMessages();
		if(userRepository.existsByEmailAddress(new EmailAddress(email))) {
			simpleMessages.addMessage("Die Email ist bereits vergeben");
		}
		if(userRepository.existsByUsername(new Username(username))) {
			simpleMessages.addMessage("Die Email ist bereits vergeben");
		}

		if(!simpleMessages.filled()) {
			return null;
		}

		return simpleMessages;
	}

	@GetMapping("/authenticated")
	public ResponseEntity isAuthenticated() {
		if(sessionStorage.isLoggedIn()) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/users")
	public ResponseEntity<SimpleMessages> register(RegistrationDto registrationDto) {
		SimpleMessages message = constructErrors(registrationDto.getEmail(), registrationDto.getUsername());
		if(message != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}

		User newUser = userService.createNewUser(registrationDto.getEmail(), registrationDto.getPassword(), registrationDto.getUsername());
		sessionStorage.identify(newUser);
		return ResponseEntity.accepted().body(new SimpleMessages("Okay"));
	}

	@PostMapping("/users/{id}/authenticated")
	public ResponseEntity login(@PathVariable String id, String password) {
		if(sessionStorage.isLoggedIn()) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
		}
		Optional<User> login = userService.login(id, password);

		if(login.isPresent()) {
			return ResponseEntity.accepted().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@DeleteMapping("/authenticated")
	public ResponseEntity logout() {
		if(!sessionStorage.isLoggedIn()) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
		}
		sessionStorage.invalidate();

		return ResponseEntity.accepted().build();
	}
}
