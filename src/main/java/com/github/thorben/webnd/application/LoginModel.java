package com.github.thorben.webnd.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.thorben.webnd.application.tech.Message;
import com.github.thorben.webnd.application.tech.MessageBroker;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class LoginModel {

	private String username;
	private String email;
	private String password;
	private String passwordConfirmation;
	@JsonIgnore
	private List<LoginModelEntry> validEntries = new ArrayList<>();

	public LoginModel(String username, String email, String password, String passwordConfirmation) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}

	public boolean canBePerformed(MessageBroker messageBroker) {
		boolean okay = true;
		if(username.isEmpty()) {
			messageBroker.addMessage(Message.once("name_error", "Der Nutzername darf nicht leer sein."));
			okay = false;
		} else {
			validEntries.add(new LoginModelEntry("username", username));
		}
		if(email.isEmpty()) {
			messageBroker.addMessage(Message.once("email_error", "Die Email darf nicht leer sein."));
			okay = false;
		} else {
			validEntries.add(new LoginModelEntry("email", email));
		}
		if(password.isEmpty()) {
			messageBroker.addMessage(Message.once("password_error", "Das Password darf nicht leer sein."));
			okay = false;
		} else {
			validEntries.add(new LoginModelEntry("password", username));
		}
		if(!passwordConfirmation.equals(password)) {
			messageBroker.addMessage(Message.once("password_confirm_error", "Die beiden eingegeben passwörter stimmen nicht überein"));
			okay = false;
		} else {
			validEntries.add(new LoginModelEntry("password_confirm", username));
		}

		return okay;
	}

	public void fillValidEntries(Model model) {
		validEntries.forEach(e -> model.addAttribute(e.key, e.value));
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	private class LoginModelEntry {
		private String key;
		private String value;

		private LoginModelEntry(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
}
