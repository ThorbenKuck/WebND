package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "BaseValue")
@Table(name = "base_values")
public class BaseValueType extends AbstractEntity<Integer> implements Type {

	@Id
	@GeneratedValue
	private Integer id;
	@MapsId
	@OneToOne
	private GameType gameType;
	@Column(name = "base_value_type_name", nullable = false)
	private String name;
	@Column(name = "base_value_type_expression")
	private String expression;
	@Column(name = "base_value_type_description")
	private String description;
	@Column(name = "base_value_required")
	private boolean required;

	public BaseValueType() {
		// JPA
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public String getExpression() {
		return expression;
	}

	@Override
	public GameType getGameType() {
		return gameType;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	@Override
	public void setRequired(boolean to) {
		this.required = to;
	}
}
