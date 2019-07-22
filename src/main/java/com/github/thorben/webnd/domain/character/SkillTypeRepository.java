package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.domain.games.GameType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillTypeRepository extends CrudRepository<SkillType, Integer> {

	List<SkillType> findAllByGameType(GameType gameType);

	Optional<SkillType> findByName(String name);

}
