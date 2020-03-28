package com.github.thorben.webnd.application

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.thorben.webnd.application.tech.Message
import com.github.thorben.webnd.application.tech.StatefulMessageBroker
import org.springframework.ui.Model
import java.util.*
import java.util.function.Consumer

data class RegistrationModel(
		val username: String,
		val email: String,
		val password: String,
		private val passwordConfirmation: String
) {

	@JsonIgnore
	private val validEntries: MutableList<LoginModelEntry> = ArrayList()
	fun canBePerformed(messageBroker: StatefulMessageBroker): Boolean {
		var okay = true
		if (username.isEmpty()) {
			messageBroker.addMessage(Message.once("name_error", "Der Nutzername darf nicht leer sein."))
			okay = false
		} else {
			validEntries.add(LoginModelEntry("username", username))
		}
		if (email.isEmpty()) {
			messageBroker.addMessage(Message.once("email_error", "Die Email darf nicht leer sein."))
			okay = false
		} else {
			validEntries.add(LoginModelEntry("email", email))
		}
		if (password.isEmpty()) {
			messageBroker.addMessage(Message.once("password_error", "Das Password darf nicht leer sein."))
			okay = false
		} else {
			validEntries.add(LoginModelEntry("password", username))
		}
		if (passwordConfirmation != password) {
			messageBroker.addMessage(Message.once("password_confirm_error", "Die beiden eingegeben passwörter stimmen nicht überein"))
			okay = false
		} else {
			validEntries.add(LoginModelEntry("password_confirm", username))
		}
		return okay
	}

	fun fillValidEntries(model: Model) {
		validEntries.forEach(Consumer { e: LoginModelEntry -> model.addAttribute(e.key, e.value) })
	}

	private data class LoginModelEntry(
			val key: String,
			val value: String
	)

}