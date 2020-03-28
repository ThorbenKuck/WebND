package com.github.thorben.webnd.application.controller

import com.github.thorben.webnd.application.tech.StatelessMessageBroker
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/")
class HomeController {

	private val messageBroker = StatelessMessageBroker()

	@GetMapping
	fun index(httpServletRequest: HttpServletRequest, model: Model): String {
		messageBroker.applyMessages(httpServletRequest, model)
		return "index"
	}

	@GetMapping("/characters")
	fun characterHome(): String {
		return "characters"
	}
}