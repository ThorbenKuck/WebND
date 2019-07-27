package com.github.thorben.webnd.application.controller.api.dto;

import java.util.ArrayList;
import java.util.List;

public class SimpleMessages {

	private final List<String> messages = new ArrayList<>();

	public SimpleMessages(String... msg) {
		List.of(msg).forEach(this::addMessage);
	}

	public void addMessage(String message) {
		messages.add("{\"msg\":\"" + message + "\"}");
	}

	public boolean filled() {
		return !messages.isEmpty();
	}

}
