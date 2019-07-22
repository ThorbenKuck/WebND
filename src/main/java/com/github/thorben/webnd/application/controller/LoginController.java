package com.github.thorben.webnd.application.controller;

import com.github.thorben.webnd.application.SessionStorage;
import com.github.thorben.webnd.application.tech.Message;
import com.github.thorben.webnd.application.tech.MessageBroker;
import com.github.thorben.webnd.domain.user.User;
import com.github.thorben.webnd.domain.user.Username;
import com.github.thorben.webnd.usecase.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Inject
	private UserService userService;
	@Resource(name = "messageBroker")
	private MessageBroker messageBroker;
	@Resource(name = "sessionStorage")
	private SessionStorage sessionStorage;

	@GetMapping
	public String login(Model model) {
		messageBroker.applyMessages(model);
		userService.createTestUser();
		return "login";
	}

	@PostMapping
	public String tryLogin(String email, String password) {
		if(!login(email, password)) {
			messageBroker.addMessage(Message.once("email_error", "Email nicht gefunden/Passwort inkorrekt"));
			messageBroker.addMessage(Message.once("password_error", "Email nicht gefunden/Passwort inkorrekt"));
			return "redirect:login";
		}
		messageBroker.addMessage(Message.once("login_message", "Herzlich willkommen " + sessionStorage.getUser().map(User::getUsername).orElse(new Username("Unbekannt"))));
		return "redirect:/";
	}

	private boolean login(String email, String password) {
		Optional<User> userOptional = userService.login(email, password);
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			sessionStorage.identify(user);

			return true;
		}

		return false;
	}
}
