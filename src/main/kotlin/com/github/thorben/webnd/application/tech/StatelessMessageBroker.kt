package com.github.thorben.webnd.application.tech

import org.springframework.ui.Model
import java.util.*
import javax.servlet.http.HttpServletRequest

open class StatelessMessageBroker {

	private fun setMessages(request: HttpServletRequest, messages: List<Message>) {
		val session = request.session
		session.setAttribute(MESSAGES_KEY, messages)
	}

	fun getMessages(request: HttpServletRequest): MutableList<Message> {
		return Optional.ofNullable(request.session.getAttribute(MESSAGES_KEY))
				.map { ArrayList(it as List<Message>) }
				.orElse(ArrayList())
	}

	fun addMessage(request: HttpServletRequest, message: Message) {
		val messages = getMessages(request)
		messages.add(message)
		setMessages(request, messages)
	}

	fun applyMessages(request: HttpServletRequest, model: Model) {
		val temp: List<Message> = getMessages(request)

		temp.filter { it.valid() }
				.forEach { model.addAttribute(it.key, it.getContent()) }

		setMessages(request, temp.filter { obj: Message -> obj.valid() }.toMutableList())
	}

	companion object {
		private const val MESSAGES_KEY = "messages"
	}
}