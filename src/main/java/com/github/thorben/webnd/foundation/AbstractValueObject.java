package com.github.thorben.webnd.foundation;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractValueObject<V extends Comparable<? super V>>
		implements Comparable<AbstractValueObject<V>>, Serializable {

	private static final String VALUE = "value";
	@Column(name = VALUE)
	private V value;

	protected AbstractValueObject() {
		// For JPA
	}

	public AbstractValueObject(V value) {
		notNull(value);
		this.value = value;
	}

	public V getValue() {
		return value;
	}

	@Override
	public int compareTo(AbstractValueObject<V> vAbstractValueObject) {
		return getValue().compareTo(vAbstractValueObject.getValue());
	}

	@Override
	public String toString() {
		return getValue().toString();
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}

		if (!(that instanceof AbstractValueObject) ||
				!(that.getClass().isAssignableFrom(getClass())) ||
				getClass().isAssignableFrom(that.getClass())) {
			return false;
		}

		AbstractValueObject<V> valueObject = (AbstractValueObject<V>) that;
		return valueObject.getValue().equals(getValue());
	}
}
