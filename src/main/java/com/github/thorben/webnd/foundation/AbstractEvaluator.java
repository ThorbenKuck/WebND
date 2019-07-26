package com.github.thorben.webnd.foundation;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class AbstractEvaluator {

	private final Map<String, BigDecimal> variables = new HashMap<>();

	void cache(String prefix, String name, Double value) {
		variables.put(prefix + "_" + name.toLowerCase(), BigDecimal.valueOf(value));
	}

	public void clear() {
		variables.clear();
	}

	String doEvaluate(String name, String rawExpression, String value, String evalType, Map<String, String> toFill) {
		if(rawExpression == null) {
			try {
				Double entry = Double.parseDouble(value);
				cache(evalType, name, entry);
				toFill.put(name, entry.toString());
				return entry.toString();
			} catch (NumberFormatException e) {
				// This special case is, that something not a number is set.
				// This might be (for example): Speed="30 ft."
				toFill.put(name, value);
				return null;
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
			return entry.toString();
		}
	}

	private Expression getExpression(String raw) {
		Expression expression = new Expression(raw).setRoundingMode(RoundingMode.HALF_DOWN);
		variables.forEach(expression::with);

		return expression;
	}

}
