package com.github.thorben.webnd.application.tech

class Message(
		val key: String,
		private val content: String,
		private var uses: Int = -1
) {

	fun getContent(): String {
		if (uses > 0) {
			uses -= 1
		}
		return content
	}

	fun valid(): Boolean {
		return uses != 0
	}

	companion object {
		fun once(key: String, value: String): Message {
			return Message(key, value, 1)
		}
	}

	init {
		require(uses != 0) { "The amount of uses has to be -1 or greater then 0" }
	}
}