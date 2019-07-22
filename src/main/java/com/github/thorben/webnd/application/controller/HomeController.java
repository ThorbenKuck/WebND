package com.github.thorben.webnd.application.controller;

import com.github.thorben.webnd.application.tech.MessageBroker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/")
public class HomeController {

	@Resource(name = "messageBroker")
	private MessageBroker messageBroker;

	@GetMapping
	public String index(Model model) {
		messageBroker.applyMessages(model);
		return "index";
	}

	@GetMapping("/characters")
	public String characterHome() {
		return "characters";
	}

}
