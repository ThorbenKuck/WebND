package com.github.thorben.webnd.domain.character

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillRepository : CrudRepository<Skill?, Int?>