package com.github.thorben.webnd.foundation;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<T> implements Serializable {

	public abstract T getId();

	@Override
	public int hashCode() {
		T id = getId();
		return id == null ? super.hashCode() : id.hashCode();
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}

		if (!(that instanceof AbstractEntity) ||
				!(that.getClass().isAssignableFrom(getClass())) ||
				getClass().isAssignableFrom(that.getClass())) {
			return false;
		}

		AbstractEntity<T> valueObject = (AbstractEntity<T>) that;
		return getId() != null && valueObject.getId().equals(getId());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + getId();
	}

}
