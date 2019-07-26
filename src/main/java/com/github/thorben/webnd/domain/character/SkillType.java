package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "SkillType")
@Table(name = "skill_types")
public class SkillType extends AbstractEntity<Integer> implements Type {

	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne
	private GameType gameType;
	@Column(name = "skill_type_name", unique = true)
	private String name;
	@Column(name = "skill_type_description")
	private String description;
	@Column(name = "skill_type_expression")
	private String expression;
	@Column(name = "skill_type_required")
	private boolean required;

	public SkillType() {
		// JPA
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public GameType getGameType() {
		return gameType;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getExpression() {
		return expression;
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
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}
}
