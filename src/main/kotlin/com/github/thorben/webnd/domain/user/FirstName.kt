package com.github.thorben.webnd.domain.user

import com.github.thorben.webnd.foundation.AbstractValueObject
import javax.persistence.Embeddable

@Embeddable
class FirstName(value: String) : AbstractValueObject<String>(value) {
	protected constructor(): this("") {
		// JPA
	}
}