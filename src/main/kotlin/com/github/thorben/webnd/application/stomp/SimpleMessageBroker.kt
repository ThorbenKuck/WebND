package com.github.thorben.webnd.application.stomp

import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class SimpleMessageBroker(
		private val simpleMessagingTemplate: SimpMessagingTemplate
) {

	private fun constructHeader(vararg headerEntries: HeaderEntry): MessageHeaders {
		val map = HashMap<String, Any>()
		headerEntries.forEach { map[it.key] = it.value }
		return MessageHeaders(map)
	}

	fun <T : Any> publish(@DestinationVariable path: String, content: T, vararg headerEntries: HeaderEntry) {
		println("Publishing $content to $path")
		simpleMessagingTemplate.convertAndSend(path, content, constructHeader(*headerEntries))
	}

	data class HeaderEntry(
			val key: String,
			val value: Any
	)
}