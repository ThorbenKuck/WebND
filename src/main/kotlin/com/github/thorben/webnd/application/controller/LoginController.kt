package com.github.thorben.webnd.application.controller

import com.github.thorben.webnd.application.SessionStorage
import com.github.thorben.webnd.application.tech.Message
import com.github.thorben.webnd.application.tech.StatelessMessageBroker
import com.github.thorben.webnd.domain.user.Username
import com.github.thorben.webnd.usecase.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/login")
class LoginController(
		private val userService: UserService
) {
	private val statelessMessageBroker = StatelessMessageBroker()

	@Resource(name = "sessionStorage")
	private lateinit var sessionStorage: SessionStorage

	@GetMapping
	fun login(request: HttpServletRequest, model: Model): String {
		statelessMessageBroker.applyMessages(request, model)
		userService.createTestUser()
		return "login"
	}

	@PostMapping
	fun tryLogin(request: HttpServletRequest, email: String, password: String): String {
		if (!login(email, password)) {
			statelessMessageBroker.addMessage(request, Message.once("email_error", "Email nicht gefunden/Passwort inkorrekt"))
			statelessMessageBroker.addMessage(request, Message.once("password_error", "Email nicht gefunden/Passwort inkorrekt"))
			return "redirect:login"
		}
		statelessMessageBroker.addMessage(request, Message.once("login_message", "Herzlich willkommen " + sessionStorage.user.map { it.username }.orElse(Username("Unbekannt"))))
		return "redirect:/"
	}

	private fun login(email: String, password: String): Boolean {
		val userOptional = userService.login(email, password)
		if (userOptional.isPresent) {
			val user = userOptional.get()
			sessionStorage.identify(user)
			return true
		}
		return false
	}
}