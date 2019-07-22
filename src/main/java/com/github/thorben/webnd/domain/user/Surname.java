package com.github.thorben.webnd.domain.user;

import com.github.thorben.webnd.foundation.AbstractValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Surname extends AbstractValueObject<String> {

	protected Surname() {
		// JPA
	}

	public Surname(String value) {
		super(value);
	}
}
