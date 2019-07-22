package com.github.thorben.webnd.domain.user;

import com.github.thorben.webnd.foundation.AbstractValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class FirstName extends AbstractValueObject<String> {

	protected FirstName() {
		// JPA
	}

	public FirstName(String value) {
		super(value);
	}
}
