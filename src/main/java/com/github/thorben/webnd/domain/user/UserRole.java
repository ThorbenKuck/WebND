package com.github.thorben.webnd.domain.user;

public enum UserRole {

	USER {
		@Override
		public String pretty() {
			return "";
		}
	}, MODERATOR {
		@Override
		public String pretty() {
			return "(Moderator)";
		}
	};

	public abstract String pretty();

}
