package com.github.thorben.webnd.domain.user;

import com.github.thorben.webnd.foundation.AbstractValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Password extends AbstractValueObject<String> {

	protected Password() {
		// JPA
	}

	public Password(String value) {
		super(value);
	}
}
