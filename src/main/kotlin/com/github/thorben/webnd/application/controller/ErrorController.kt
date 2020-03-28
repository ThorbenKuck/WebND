package com.github.thorben.webnd.application.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

@Controller
class ErrorController : ErrorController {
	@RequestMapping("/error")
	fun handleError(request: HttpServletRequest, model: Model): String {
		val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)
		if (status != null) {
			when (status.toString().toInt()) {
				HttpStatus.NOT_FOUND.value() -> model.addAttribute("error_message", "Würfel Survival: 1: Du hast dich verrirt")
				HttpStatus.INTERNAL_SERVER_ERROR.value() -> model.addAttribute("error_message", "Ein interner Fehler überfällt dich! Mache einen Constituion Saving Throw: 1")
				else -> model.addAttribute("error_message", "Passive wisdom(perception) is zu niedrieg, um die Fehlerart zu erkennen")
			}
		}
		return "error"
	}

	override fun getErrorPath(): String {
		return "/error"
	}
}