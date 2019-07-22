package com.github.thorben.webnd.application.controller;

import com.github.thorben.webnd.application.SessionStorage;
import com.github.thorben.webnd.application.tech.Message;
import com.github.thorben.webnd.application.tech.MessageBroker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@Resource(name = "sessionStorage")
	private SessionStorage sessionStorage;
	@Resource(name = "messageBroker")
	private MessageBroker messageBroker;

	@RequestMapping
	public String logout() {
		sessionStorage.invalidate();
		messageBroker.addMessage(Message.once("logout_message", "Du hast dich erfolgreich abgemeldet"));
		return "redirect:/";
	}
}
