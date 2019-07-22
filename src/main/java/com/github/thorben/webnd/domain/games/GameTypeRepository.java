package com.github.thorben.webnd.domain.games;

import com.github.thorben.webnd.domain.character.AttributeType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameTypeRepository extends CrudRepository<GameType, Integer> {

}
