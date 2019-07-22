package com.github.thorben.webnd.application.controller.api.dto;

import com.github.thorben.webnd.domain.character.SkillType;

import java.util.List;

public class SkillTypeList {

	private final List<SkillType> skillTypes;

	public SkillTypeList(List<SkillType> skillTypes) {
		this.skillTypes = skillTypes;
	}

	public List<SkillType> getSkillTypes() {
		return skillTypes;
	}
}
