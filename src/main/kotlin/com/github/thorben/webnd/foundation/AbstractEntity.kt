package com.github.thorben.webnd.foundation

import java.io.Serializable
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity<T> : Serializable {
	abstract val id: T
	override fun hashCode(): Int {
		val id = id
		return id?.hashCode() ?: super.hashCode()
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}
		if (other !is AbstractEntity<*> ||
				!other.javaClass.isAssignableFrom(javaClass) ||
				javaClass.isAssignableFrom(other.javaClass)) {
			return false
		}
		val valueObject = other as AbstractEntity<T>
		return id != null && valueObject.id == id
	}

	override fun toString(): String {
		return javaClass.simpleName + "#" + id
	}
}