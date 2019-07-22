package com.github.thorben.webnd.domain.character;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseValueRepository extends CrudRepository<BaseValue, Integer> {
}
