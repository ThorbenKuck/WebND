package com.github.thorben.webnd.application.tech

import org.springframework.ui.Model
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.ArrayList

open class StatefulMessageBroker(
		private val request: HttpServletRequest
) {

	private val messages = Optional.ofNullable(request.session.getAttribute(MESSAGES_KEY))
			.map { ArrayList(it as List<Message>) }
			.orElse(ArrayList())

	private fun setMessages(messages: List<Message>) {
		this.messages.clear()
		this.messages.addAll(messages)
	}

	fun getMessages(): MutableList<Message> {
		return ArrayList(messages)
	}

	fun addMessage(message: Message) {
		this.messages.add(message)
	}

	fun applyMessages(model: Model) {
		val temp: List<Message> = getMessages()

		temp.filter { it.valid() }
				.forEach { model.addAttribute(it.key, it.getContent()) }

		setMessages(temp.filter { obj: Message -> obj.valid() }.toMutableList())
	}

	fun fetch() {
		messages.clear()
		messages.addAll(Optional.ofNullable(request.session.getAttribute(MESSAGES_KEY))
				.map { java.util.ArrayList(it as List<Message>) }
				.orElse(java.util.ArrayList()))
	}

	fun flush() {
		val session = request.session
		session.setAttribute(MESSAGES_KEY, messages)
	}

	companion object {
		private const val MESSAGES_KEY = "messages"
	}
}