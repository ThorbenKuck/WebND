package com.github.thorben.webnd.usecase;

import com.github.thorben.webnd.domain.user.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class UserService {

	@Inject
	private UserRepository userRepository;

	public boolean emailExists(String rawEmail) {
		return userRepository.findByEmailAddress(new EmailAddress(rawEmail)).isPresent();
	}

	public User createNewUser(String email, String password, String username) {
		String salt = BCrypt.gensalt();
		String hashedPassword = BCrypt.hashpw(password, salt);
		User user = new User(new EmailAddress(email), new Password(hashedPassword), new Username(username), UserRole.USER);
		userRepository.save(user);

		return user;
	}

	public Optional<User> login(String email, String password) {
		EmailAddress emailAddress = new EmailAddress(email);
		Optional<User> userOptional = userRepository.findByEmailAddress(emailAddress);
		if(!userOptional.isPresent()) {
			return Optional.empty();
		}
		User user = userOptional.get();
		Password storedPassword = user.getPassword();

		if(BCrypt.checkpw(password, storedPassword.getValue())) {
			return userOptional;
		} else {
			return Optional.empty();
		}
	}

	private boolean testCreated;

	public void createTestUser() {
		if(!testCreated) {
			User user = new User(new EmailAddress("test@test.test"), new Password(BCrypt.hashpw("test", BCrypt.gensalt())), new Username("Test"), UserRole.MODERATOR);
			userRepository.save(user);
			testCreated = true;
		}
	}
}
