package com.github.thorben.webnd.domain.games;

import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "GameType")
@Table(name = "game_types")
public class GameType extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "game_type_description")
	private String description;
	@Column(name = "game_type_name")
	private String name;

	protected GameType() {
		// JPA
	}

	public GameType(String description, String name) {
		this.description = description;
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@Override
	public Integer getId() {
		return id;
	}
}
