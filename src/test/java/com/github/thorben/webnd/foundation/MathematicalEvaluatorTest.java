package com.github.thorben.webnd.foundation;

import com.github.thorben.webnd.domain.character.Attribute;
import com.github.thorben.webnd.domain.character.AttributeType;
import com.github.thorben.webnd.domain.character.Skill;
import com.github.thorben.webnd.domain.character.SkillType;
import com.github.thorben.webnd.domain.games.GameType;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MathematicalEvaluatorTest {

	@Test
	public void test() {
		MathematicalEvaluator mathematicalEvaluator = new MathematicalEvaluator();
		GameType gameType = new GameType("blabla", "DnD 5e");
		AttributeType attributeType = new AttributeType();
		attributeType.setGameType(gameType);
		attributeType.setName("Strength");

		List<Attribute> attributes = Collections.singletonList(new Attribute(attributeType, "15"));

		Map<String, String> attributeMap = mathematicalEvaluator.evaluateAttributes(attributes);

		SkillType skillType = new SkillType();
		skillType.setGameType(gameType);
		skillType.setName("Acrobatics");
		skillType.setExpression("FLOOR((attribute_strength - 10) / 2)");
		List<Skill> skills = Collections.singletonList(new Skill(skillType, ""));

		Map<String, String> skillMap = mathematicalEvaluator.evaluateSkills(skills);

		System.out.println(attributeMap);
		System.out.println(skillMap);
	}

}