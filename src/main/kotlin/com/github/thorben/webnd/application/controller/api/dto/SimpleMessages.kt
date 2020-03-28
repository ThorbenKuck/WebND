package com.github.thorben.webnd.application.controller.api.dto

import java.util.*
import java.util.function.Consumer

class SimpleMessages(vararg msg: String) {

	init {
		listOf(*msg).forEach { addMessage(it) }
	}

	private val messages: MutableList<String> = ArrayList()

	fun addMessage(message: String) {
		messages.add("{\"msg\":\"$message\"}")
	}

	fun filled(): Boolean {
		return messages.isNotEmpty()
	}
}