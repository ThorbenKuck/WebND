package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.domain.user.User;
import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Attributes are not evaluated. Those are (for example) something like Strength.
 * BaseValues are directly inherited from Attributes. This is (for example) Life/Mana and so on.
 * Skills are abilities of characters, which are directly influenced from Attributes. This is (for example) Perception
 * Details cannot be evaluated. This is something like Age and Height.
 */
@Entity(name = "Character")
@Table(name = "character")
public class Character extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@OneToOne
	@MapsId
	private GameType gameType;
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Attribute> attributes;
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Skill> skills;
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Detail> details;
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private List<BaseValue> baseValues;
	@Column(name = "character_name", nullable = false)
	private String name;

	public Character() {
	}

	@Override
	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public GameType getGameType() {
		return gameType;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
	}

	public List<BaseValue> getBaseValues() {
		return baseValues;
	}

	public void setBaseValues(List<BaseValue> baseValues) {
		this.baseValues = baseValues;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
