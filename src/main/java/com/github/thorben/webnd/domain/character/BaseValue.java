package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "BaseValue")
@Table(name = "base_value")
public class BaseValue extends AbstractEntity<Integer> implements TypeFill {

	@Id
	@GeneratedValue
	private Integer id;
	@JoinColumn(name = "id", table = "skill_types")
	private SkillType type;
	@Column(name = "skill_value")
	private String value;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_id")
	private Character character;

	protected BaseValue() {
		// JPA
	}

	public BaseValue(SkillType type, String value) {
		this.type = type;
		this.value = value;
	}

	public SkillType getType() {
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
