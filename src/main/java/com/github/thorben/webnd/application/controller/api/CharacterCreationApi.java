package com.github.thorben.webnd.application.controller.api;

import com.github.thorben.webnd.domain.character.SkillType;
import com.github.thorben.webnd.domain.character.SkillTypeRepository;
import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.domain.games.GameTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(name = "/api/game_types/{gameId}")
public class CharacterCreationApi {

	@Inject
	private SkillTypeRepository skillTypeRepository;
	@Inject
	private GameTypeRepository gameTypeRepository;

	@GetMapping("/skill_types")
	public ResponseEntity getAllSkillTypes(@PathVariable Integer gameId) {
		Optional<GameType> gameType = gameTypeRepository.findById(gameId);
		if(!gameType.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		List<SkillType> allByGameType = skillTypeRepository.findAllByGameType(gameType.get());

		return new ResponseEntity(allByGameType, HttpStatus.OK);
	}

}
