package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "DetailsType")
@Table(name = "detail_types")
public class DetailType extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue
	private Integer id;
	@OneToOne
	private GameType gameType;
	@Column(name = "detail_type_name", unique = true)
	private String name;
	@Column(name = "detail_type_description")
	private String description;
	@Column(name = "detail_type__required")
	private boolean required;

	protected DetailType() {
		// JPA
	}

	public DetailType(GameType gameType, String name, String description) {
		this.gameType = gameType;
		this.name = name;
		this.description = description;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public GameType getGameType() {
		return gameType;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
}
