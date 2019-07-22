package com.github.thorben.webnd.application;

import com.github.thorben.webnd.domain.character.*;
import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.domain.games.GameTypeRepository;
import com.github.thorben.webnd.domain.user.User;
import com.github.thorben.webnd.domain.user.UserRole;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public void identify(User user) {
		this.user = user;
	}

	public Optional<User> getUser() {
		return Optional.ofNullable(user);
	}

	public List<SkillType> loadSkillTypes() {
		List<SkillType> types = new ArrayList<>();
		skillTypeRepository.findAll().forEach(types::add);

		return types;
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
