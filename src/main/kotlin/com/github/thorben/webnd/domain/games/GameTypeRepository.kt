package com.github.thorben.webnd.domain.games

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GameTypeRepository : CrudRepository<GameType, Int>