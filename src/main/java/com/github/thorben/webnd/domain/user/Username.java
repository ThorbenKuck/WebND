package com.github.thorben.webnd.domain.user;

import com.github.thorben.webnd.foundation.AbstractValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Username extends AbstractValueObject<String> {

	protected Username() {
		// JPA
	}

	public Username(String value) {
		super(value);
	}
}
