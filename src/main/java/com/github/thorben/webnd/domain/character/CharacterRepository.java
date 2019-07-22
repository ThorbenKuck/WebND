package com.github.thorben.webnd.domain.character;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Integer> {
}
