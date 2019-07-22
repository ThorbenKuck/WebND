package com.github.thorben.webnd.foundation;

import com.github.thorben.webnd.domain.character.Attribute;
import com.github.thorben.webnd.domain.character.BaseValue;
import com.github.thorben.webnd.domain.character.Skill;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathematicalEvaluator {

	private final Map<String, BigDecimal> variables = new HashMap<>();

	private void cache(String prefix, String name, Double value) {
		variables.put(prefix + "_" + name.toLowerCase(), BigDecimal.valueOf(value));
	}

	private void doEvaluate(String name, String rawExpression, String value, String evalType, Map<String, String> toFill) {
		if(rawExpression == null) {
			try {
				Double entry = Double.parseDouble(value);
				cache(evalType, name, entry);
				toFill.put(name, entry.toString());
			} catch (NumberFormatException e) {
				// This special case is, that something not a number is set.
				// This might be (for example): Speed=30 ft.
				toFill.put(name, value);
			}
		} else {
			if(value != null) {
				try {
					double valueDouble = Double.parseDouble(value);
					rawExpression = "(" + rawExpression + ") + " + valueDouble;
				} catch (NumberFormatException ignored) {}
			}
			Expression expression = getExpression(rawExpression);
			BigDecimal eval = expression.eval();
			Double entry = eval.doubleValue();
			cache(evalType, name, entry);
			toFill.put(name, entry.toString());
		}
	}

	private Expression getExpression(String raw) {
		Expression expression = new Expression(raw).setRoundingMode(RoundingMode.HALF_DOWN);
		variables.forEach(expression::with);

		return expression;
	}

	public void clear() {
		variables.clear();
	}

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
