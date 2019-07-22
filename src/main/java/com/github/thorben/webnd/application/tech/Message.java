package com.github.thorben.webnd.application.tech;

public class Message {

	private String key;
	private String content;
	private int uses;

	public Message(String key, String content, int uses) {
		this.key = key;
		this.content = content;
		this.uses = uses;
		if(uses == 0) {
			throw new IllegalArgumentException("The amount of uses has to be -1 or greater then 0");
		}
	}

	public Message(String key, String content) {
		this(key, content, -1);
	}

	public static Message once(String key, String value) {
		return new Message(key, value, 1);
	}

	public String getContent() {
		if(uses > 0) {
			uses -= 1;
		}

		return content;
	}

	public boolean valid() {
		return uses != 0;
	}

	public String getKey() {
		return key;
	}
}
