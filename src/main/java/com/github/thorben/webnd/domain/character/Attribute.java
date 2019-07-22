package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "Attribute")
@Table(name = "attributes")
public class Attribute extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue
	private Integer id;
	@MapsId
	@JoinColumn(name = "id")
	private AttributeType type;
	@Column(name = "attribute_value")
	private String value;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_id")
	private Character character;

	protected Attribute() {
		// JPA
	}

	public Attribute(AttributeType type, String value) {
		this.type = type;
		this.value = value;
	}

	public AttributeType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public Integer getId() {
		return id;
	}
}
