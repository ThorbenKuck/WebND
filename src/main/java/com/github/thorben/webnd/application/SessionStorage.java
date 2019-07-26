package com.github.thorben.webnd.application;

import com.github.thorben.webnd.application.dto.CreationDto;
import com.github.thorben.webnd.domain.character.*;
import com.github.thorben.webnd.domain.character.Character;
import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.domain.games.GameTypeRepository;
import com.github.thorben.webnd.domain.user.User;
import com.github.thorben.webnd.domain.user.UserRole;
import com.github.thorben.webnd.foundation.MathematicalCharacterCreationEvaluator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionStorage {

	private User user;
	@Inject
	private SkillTypeRepository skillTypeRepository;
	@Inject
	private AttributeTypeRepository attributeTypeRepository;
	@Inject
	private DetailTypeRepository detailTypeRepository;
	@Inject
	private BaseValueTypeRepository baseValueTypeRepository;
	@Inject
	private GameTypeRepository gameTypeRepository;
	private Character currentCharacter;
	private MathematicalCharacterCreationEvaluator evaluator;

	public void identify(User user) {
		this.user = user;
	}

	public Optional<User> getUser() {
		return Optional.ofNullable(user);
	}

	public List<CreationDto> generateAttributeCreationDto() {
		if (currentCharacter == null) {
			return Collections.emptyList();
		}

		return attributeTypeRepository.findAllByGameType(currentCharacter.getGameType())
				.stream()
				.map(type -> new CreationDto(type.getName(), type.getDescription(), type.getId(), "", type.isRequired()))
				.collect(Collectors.toList());
	}

	public void prepareCharacterCreation(String name, GameType gameType) {
		currentCharacter = new Character();
		evaluator = new MathematicalCharacterCreationEvaluator();
		currentCharacter.setName(name);
		currentCharacter.setGameType(gameType);
	}

	public void characterCreationSetAttributes(List<Attribute> attributes) {
		if (currentCharacter == null) {
			throw new IllegalStateException("Du befindest dich grade nicht in der Charakter erstellung!");
		}

		evaluator.storeAttributes(attributes);
		currentCharacter.setAttributes(attributes);
	}

	public void characterCreationSetBaseValues(List<BaseValue> baseValues) {
		if (currentCharacter == null) {
			throw new IllegalStateException("Du befindest dich grade nicht in der Charakter erstellung!");
		}

		evaluator.storeBaseValues(baseValues);
		currentCharacter.setBaseValues(baseValues);
	}

	public void characterCreationSetDetails(List<Detail> details) {
		if (currentCharacter == null) {
			throw new IllegalStateException("Du befindest dich grade nicht in der Charakter erstellung!");
		}

		currentCharacter.setDetails(details);
	}

	public void characterCreationSetSkills(List<Skill> skills) {
		if (currentCharacter == null) {
			throw new IllegalStateException("Du befindest dich grade nicht in der Charakter erstellung!");
		}

		evaluator.storeSkills(skills);
		currentCharacter.setSkills(skills);
	}

	public List<SkillType> loadSkillTypes() {
		List<SkillType> types = new ArrayList<>();
		skillTypeRepository.findAll().forEach(types::add);

		return types;
	}

	public List<CreationDto> getSkillCreationDtoList() {
		return evaluator.createSkillCreations(loadSkillTypes());
	}

	public List<CreationDto> getBaseValueCreationDtoList() {
		return evaluator.generateBaseValueCreations(loadBaseValueTypes());
	}

	public List<AttributeType> loadAttributeTypes() {
		List<AttributeType> types = new ArrayList<>();
		attributeTypeRepository.findAll().forEach(types::add);

		return types;
	}

	public List<DetailType> loadDetailTypes() {
		List<DetailType> types = new ArrayList<>();
		detailTypeRepository.findAll().forEach(types::add);

		return types;
	}

	public List<BaseValueType> loadBaseValueTypes() {
		List<BaseValueType> types = new ArrayList<>();
		baseValueTypeRepository.findAll().forEach(types::add);

		return types;
	}

	public List<GameType> loadGameTypes() {
		List<GameType> types = new ArrayList<>();
		gameTypeRepository.findAll().forEach(types::add);

		return types;
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public boolean isModerator() {
		return user != null && user.getUserRole() == UserRole.MODERATOR;
	}

	public String prettyPrint() {
		return user == null ? "Nicht angemeldet" : (user.getUsername() + user.getUserRole().pretty());
	}

	public void invalidate() {
		user = null;
	}
}
