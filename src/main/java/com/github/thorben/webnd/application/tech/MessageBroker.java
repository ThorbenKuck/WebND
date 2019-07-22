package com.github.thorben.webnd.application.tech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageBroker {

	private static final String MESSAGES_KEY = "messages";
	private @Autowired HttpServletRequest request;

	private List<Message> getMessages() {
		return Optional.ofNullable(request.getSession().getAttribute(MESSAGES_KEY))
				.map(List.class::cast)
				.orElse(new ArrayList<Message>());
	}

	private void setMessages(List<Message> messages) {
		HttpSession session = request.getSession();
		session.setAttribute(MESSAGES_KEY, messages);
	}

	public void addMessage(Message message) {
		List<Message> messages = getMessages();
		messages.add(message);

		setMessages(messages);
	}

	public void applyMessages(Model model) {
		List<Message> messages = getMessages();
		messages.stream()
				.filter(Message::valid)
				.forEach(m -> model.addAttribute(m.getKey(), m.getContent()));

		List<Message> valid = messages.stream()
				.filter(Message::valid)
				.collect(Collectors.toList());

		setMessages(valid);
	}
}
