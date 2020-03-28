package com.github.thorben.webnd.application.controller

import com.github.thorben.webnd.application.RegistrationModel
import com.github.thorben.webnd.application.SessionStorage
import com.github.thorben.webnd.application.tech.Message
import com.github.thorben.webnd.application.tech.StatefulMessageBroker
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
@RequestMapping("/register")
class RegisterController(
		private val userService: UserService
) {

	@Resource(name = "sessionStorage")
	private lateinit var sessionStorage: SessionStorage

	@GetMapping
	fun register(): String {
		return "register"
	}

	@PostMapping
	fun newRegistration(request: HttpServletRequest, model: Model, registrationModel: RegistrationModel): String {
		val messageBroker = StatefulMessageBroker(request)

		if (!registrationModel.canBePerformed(messageBroker)) {
			messageBroker.applyMessages(model)
			registrationModel.fillValidEntries(model)
			return "/register"
		}
		if (userService.emailExists(registrationModel.email)) {
			messageBroker.addMessage(Message.once("email_error", "Die Email ist bereits vergeben"))
			messageBroker.applyMessages(model)
			registrationModel.fillValidEntries(model)
			return "/register"
		}
		val newUser = userService.createNewUser(registrationModel.email, registrationModel.password, registrationModel.username)
		sessionStorage.identify(newUser)
		messageBroker.addMessage(Message.once("message", "Du hast dich erfolgreich registriert"))
		messageBroker.addMessage(Message.once("login_message", "Herzlich willkommen " + sessionStorage.user.map { it.username }.orElse(Username("Unbekannt"))))

		messageBroker.flush()
		return "redirect:/"
	}
}