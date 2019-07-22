package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeTypeRepository extends CrudRepository<AttributeType, Integer> {

	List<AttributeType> findAllByGameType(GameType gameType);

}
