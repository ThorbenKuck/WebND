package com.github.thorben.webnd.foundation

import java.io.Serializable
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
@Access(AccessType.FIELD)
abstract class AbstractValueObject<V : Any> : Serializable {
	@Column(name = VALUE)
	lateinit var value: V
		private set

	protected constructor() {
		// For JPA
	}

	constructor(value: V) {
		this.value = value
	}

	override fun toString(): String {
		return value.toString()
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}
		if(other == null) {
			return false
		}
		if (other !is AbstractValueObject<*> ||
				!other.javaClass.isAssignableFrom(javaClass) ||
				javaClass.isAssignableFrom(other.javaClass)) {
			return false
		}
		val valueObject = other as AbstractValueObject<V>
		return valueObject.value == value
	}

	companion object {
		private const val VALUE = "value"
	}
}