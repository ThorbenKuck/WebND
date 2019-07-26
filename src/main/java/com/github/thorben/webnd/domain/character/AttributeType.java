package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "AttributeTypes")
@Table(name = "attribute_types")
public class AttributeType extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne
	private GameType gameType;
	@Column(name = "attribute_type_name")
	private String name;
	@Column(name = "attribute_type_description")
	private String description;
	@Column(name = "attribute_type_required")
	private boolean required;

	public AttributeType() {
		// JAP
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
}
