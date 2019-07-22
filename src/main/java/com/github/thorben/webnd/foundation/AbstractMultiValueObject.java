package com.github.thorben.webnd.foundation;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Arrays;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractMultiValueObject implements Serializable {

	private transient Object[] values;

	@Override
	public int hashCode() {
		return Arrays.hashCode(values);
	}

	@Override
	public boolean equals(Object object) {
		if(this == object) {
			return true;
		}

		if (!(object instanceof AbstractValueObject) ||
				!(object.getClass().isAssignableFrom(getClass())) ||
				getClass().isAssignableFrom(object.getClass())) {
			return false;
		}

		AbstractMultiValueObject valueObject = (AbstractMultiValueObject) object;
		return Arrays.equals(getValues(), valueObject.getValues());
	}

	protected abstract Object[] values();

	private Object[] getValues() {
		if(values == null) {
			values = values();
		}

		return values;
	}

}
