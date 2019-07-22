package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;

public interface Type {

	String getName();

	String getDescription();

	String getExpression();

	GameType getGameType();

	boolean isRequired();

	void setName(String name);

	void setDescription(String description);

	void setExpression(String expression);

	void setGameType(GameType gameType);

	void setRequired(boolean to);

}
