package com.github.thorben.webnd.foundation;

import com.github.thorben.webnd.domain.character.Attribute;
import com.github.thorben.webnd.domain.character.BaseValue;
import com.github.thorben.webnd.domain.character.Skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathematicalEvaluator extends AbstractEvaluator {

	public Map<String, String> evaluateAttributes(List<Attribute> attributeList) {
		final Map<String, String> result = new HashMap<>();

		attributeList.forEach(attribute -> doEvaluate(attribute.getType().getName(), null, attribute.getValue(), "attribute", result));

		return result;
	}

	public Map<String, String> evaluate(List<BaseValue> baseValueTypes) {
		final Map<String, String> result = new HashMap<>();

		baseValueTypes.forEach(baseValueType -> doEvaluate(baseValueType.getType().getName(), baseValueType.getType().getExpression(), baseValueType.getValue(), "base", result));

		return result;
	}

	public Map<String, String> evaluateSkills(List<Skill> skills) {
		final Map<String, String> result = new HashMap<>();

		skills.forEach(skill -> doEvaluate(skill.getType().getName(), skill.getType().getExpression(), skill.getValue(), "skill", result));

		return result;
	}

}
