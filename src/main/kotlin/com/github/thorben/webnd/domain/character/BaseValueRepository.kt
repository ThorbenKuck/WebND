package com.github.thorben.webnd.domain.character

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseValueRepository : CrudRepository<BaseValue?, Int?>