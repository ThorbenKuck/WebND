package com.github.thorben.webnd.application.controller

import com.github.thorben.webnd.application.SessionStorage
import com.github.thorben.webnd.application.tech.Message
import com.github.thorben.webnd.application.tech.StatelessMessageBroker
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/logout")
class LogoutController {

	@Resource(name = "sessionStorage")
	private lateinit var sessionStorage: SessionStorage

	private var statelessMessageBroker: StatelessMessageBroker = StatelessMessageBroker()

	@RequestMapping
	fun logout(request: HttpServletRequest): String {
		sessionStorage.invalidate()
		statelessMessageBroker.addMessage(request, Message.once("logout_message", "Du hast dich erfolgreich abgemeldet"))
		return "redirect:/"
	}
}