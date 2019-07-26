package com.github.thorben.webnd.foundation;

import com.github.thorben.webnd.application.dto.CreationDto;
import com.github.thorben.webnd.domain.character.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MathematicalCharacterCreationEvaluator extends AbstractEvaluator {

	public void storeAttributes(List<Attribute> attributeList) {
		attributeList.forEach(attribute -> {
			try {
				Double value = Double.parseDouble(attribute.getValue());
				cache("attribute", attribute.getType().getName(), value);
			} catch (NumberFormatException ignored) {
			}
		});
	}

	public void storeBaseValues(List<BaseValue> baseValues) {
		baseValues.forEach(attribute -> {
			try {
				Double value = Double.parseDouble(attribute.getValue());
				cache("base", attribute.getType().getName(), value);
			} catch (NumberFormatException ignored) {
			}
		});
	}

	public void storeSkills(List<Skill> baseValues) {
		baseValues.forEach(attribute -> {
			try {
				Double value = Double.parseDouble(attribute.getValue());
				cache("skill", attribute.getType().getName(), value);
			} catch (NumberFormatException ignored) {
			}
		});
	}

	public List<CreationDto> generateBaseValueCreations(List<BaseValueType> baseValueTypes) {
		List<CreationDto> result = new ArrayList<>();

		baseValueTypes.forEach(type -> {
			String attribute = doEvaluate(type.getName(), type.getExpression(), "", "base", new HashMap<>());
			CreationDto creationDto = new CreationDto(type.getName(), type.getDescription(), type.getId(), attribute, type.isRequired());
			result.add(creationDto);
		});

		return result;
	}

	public List<CreationDto> createSkillCreations(List<SkillType> skills) {
		List<CreationDto> result = new ArrayList<>();

		skills.forEach(type -> {
			String attribute = doEvaluate(type.getName(), type.getExpression(), "", "skill", new HashMap<>());
			CreationDto creationDto = new CreationDto(type.getName(), type.getDescription(), type.getId(), attribute, type.isRequired());
			result.add(creationDto);
		});

		return result;
	}

}
