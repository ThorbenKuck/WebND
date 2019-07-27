package com.github.thorben.webnd.application.controller;

import com.github.thorben.webnd.application.RegistrationModel;
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

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Inject
	private UserService userService;
	@Resource(name = "messageBroker")
	private MessageBroker messageBroker;
	@Resource(name = "sessionStorage")
	private SessionStorage sessionStorage;

	@GetMapping
	public String register() {
		return "register";
	}

	@PostMapping
	public String newRegistration(Model model, RegistrationModel registrationModel) {
		if(!registrationModel.canBePerformed(messageBroker)) {
			messageBroker.applyMessages(model);
			registrationModel.fillValidEntries(model);
			return "/register";
		}
		if(userService.emailExists(registrationModel.getEmail())) {
			messageBroker.addMessage(Message.once("email_error", "Die Email ist bereits vergeben"));
			messageBroker.applyMessages(model);
			registrationModel.fillValidEntries(model);
			return "/register";
		}
		User newUser = userService.createNewUser(registrationModel.getEmail(), registrationModel.getPassword(), registrationModel.getUsername());
		sessionStorage.identify(newUser);
		messageBroker.addMessage(Message.once("message", "Du hast dich erfolgreich registriert"));
		messageBroker.addMessage(Message.once("login_message", "Herzlich willkommen " + sessionStorage.getUser().map(User::getUsername).orElse(new Username("Unbekannt"))));
		return "redirect:/";
	}

}
