package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailTypeRepository extends CrudRepository<DetailType, Integer> {

	List<DetailType> findAllByGameType(GameType gameType);

}
