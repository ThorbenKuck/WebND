package com.github.thorben.webnd.application.dto;

import com.github.thorben.webnd.domain.character.Attribute;
import com.github.thorben.webnd.domain.character.AttributeType;
import com.github.thorben.webnd.domain.character.Type;
import com.github.thorben.webnd.foundation.MathematicalEvaluator;

import java.util.List;
import java.util.stream.Collectors;

public class CreationFactory {

	private MathematicalEvaluator mathematicalEvaluator;

	public void store(List<Attribute> attributes) {
		mathematicalEvaluator.evaluateAttributes(attributes);
	}

	public List<CreationDto> map(List<AttributeType> attributeTypes) {
		return attributeTypes.stream()
				.map(type -> new CreationDto(type.getName(),
						type.getDescription(),
						type.getId(),
						"",
						type.isRequired()))
				.collect(Collectors.toList());
	}

	public List<CreationDto> mapAnyType(List<Type> attributeTypes) {
		return attributeTypes.stream()
				.map(type -> new CreationDto(type.getName(),
						type.getDescription(),
						type.getId(),
						"",
						type.isRequired()))
				.collect(Collectors.toList());
	}

}
