package com.github.thorben.webnd.domain.user

enum class UserRole {
	USER {
		override fun pretty(): String {
			return ""
		}
	},
	MODERATOR {
		override fun pretty(): String {
			return "(Moderator)"
		}
	};

	abstract fun pretty(): String
}