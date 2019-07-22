package com.github.thorben.webnd.domain.user;

import com.github.thorben.webnd.foundation.AbstractValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class EmailAddress extends AbstractValueObject<String> {

	protected EmailAddress() {
		// JPA
	}

	public EmailAddress(String value) {
		super(value);
	}
}
