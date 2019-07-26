package com.github.thorben.webnd.application.controller.api;

import com.github.thorben.webnd.application.SessionStorage;
import com.github.thorben.webnd.application.controller.api.dto.SkillTypeList;
import com.github.thorben.webnd.application.dto.CreationDto;
import com.github.thorben.webnd.domain.character.*;
import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.domain.games.GameTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class GameSpecificApi {

	@Inject
	private SkillTypeRepository skillTypeRepository;
	@Inject
	private DetailTypeRepository detailTypeRepository;
	@Inject
	private AttributeTypeRepository attributeTypeRepository;
	@Inject
	private GameTypeRepository gameTypeRepository;
	@Inject
	private BaseValueTypeRepository baseValueRepository;
	@Inject
	private SessionStorage sessionStorage;

	@GetMapping(value = "/api/game_types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<GameType>> getAllGameTypes() {
		List<GameType> result = new ArrayList<>();
		gameTypeRepository.findAll().forEach(result::add);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/api/game_types/{gameId}/skills", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<SkillType>> getAllSkills(@PathVariable Integer gameId) {
		Optional<GameType> byId = gameTypeRepository.findById(gameId);
		if (!byId.isPresent()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		return ResponseEntity.ok(skillTypeRepository.findAllByGameType(byId.get()));
	}

	@PostMapping("/api/characterCreation/{gameId}")
	public ResponseEntity prepareCharacterCreation(@PathVariable Integer id, String characterName) {
		Optional<GameType> byId = gameTypeRepository.findById(id);
		if(!byId.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		sessionStorage.prepareCharacterCreation(characterName, byId.get());
		return ResponseEntity.accepted().build();
	}

	@GetMapping(value = "/api/game_types/{gameId}/skillCreation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CreationDto>> getAllSkillCreations() {
		return ResponseEntity.ok(sessionStorage.getSkillCreationDtoList());
	}

	@GetMapping(value = "/api/game_types/{gameId}/baseValueCreation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CreationDto>> getAllBaseValueCreations() {
		return ResponseEntity.ok(sessionStorage.getBaseValueCreationDtoList());
	}

	@GetMapping(value = "/api/game_types/{gameId}/abilities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttributeType>> getAllAttributes(@PathVariable Integer gameId) {
		Optional<GameType> byId = gameTypeRepository.findById(gameId);
		if (!byId.isPresent()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		return ResponseEntity.ok(attributeTypeRepository.findAllByGameType(byId.get()));
	}

	@GetMapping(value = "/api/game_types/{gameId}/baseValues", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<BaseValueType>> getAllBaseValues(@PathVariable Integer gameId) {
		Optional<GameType> byId = gameTypeRepository.findById(gameId);
		if (!byId.isPresent()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		return ResponseEntity.ok(baseValueRepository.findAllByGameType(byId.get()));
	}

	@GetMapping(value = "/api/game_types/{gameId}/details", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<DetailType>> getAllDetails(@PathVariable Integer gameId) {
		Optional<GameType> byId = gameTypeRepository.findById(gameId);
		if (!byId.isPresent()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		return ResponseEntity.ok(detailTypeRepository.findAllByGameType(byId.get()));
	}

	@PostMapping(value = "/api/game_types/{gameId}/skills", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity newSkillType(String name, String description, String expression, boolean skillRequired, @PathVariable Integer gameId) {
		Optional<GameType> gameType = gameTypeRepository.findById(gameId);
		if (!gameType.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		if (skillTypeRepository.findByName(name).isPresent()) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}

		SkillType skillType = new SkillType();
		skillType.setRequired(skillRequired);
		skillType.setGameType(gameType.get());
		skillType.setName(name);
		skillType.setExpression(expression);
		skillType.setDescription(description);

		skillTypeRepository.save(skillType);
		List<SkillType> skillTypes = new ArrayList<>();
		skillTypeRepository.findAll().forEach(skillTypes::add);
		return ResponseEntity.accepted().body(new SkillTypeList(skillTypes));
	}

	@PostMapping(value = "/api/game_types/{gameId}/abilities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity newAttributeType(String name, String description, boolean abilityRequired, @PathVariable Integer gameId) {
		Optional<GameType> gameType = gameTypeRepository.findById(gameId);
		if (!gameType.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		AttributeType attributeType = new AttributeType();
		attributeType.setGameType(gameType.get());
		attributeType.setRequired(abilityRequired);
		attributeType.setName(name);
		attributeType.setDescription(description);

		attributeTypeRepository.save(attributeType);
		List<AttributeType> abilityTypes = new ArrayList<>();
		attributeTypeRepository.findAll().forEach(abilityTypes::add);
		return ResponseEntity.ok(abilityTypes);
	}

	@PostMapping(value = "/api/game_types/{gameId}/details", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity newCharacterDetail(String name, String description, boolean detailRequired, @PathVariable Integer gameId) {
		Optional<GameType> gameType = gameTypeRepository.findById(gameId);
		if (!gameType.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		DetailType detailType = new DetailType(gameType.get(), name, description);
		detailType.setRequired(detailRequired);
		detailTypeRepository.save(detailType);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping(value = "/api/game_types/{gameId}/baseValues", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity newBaseValue(String name, String description, String expression, boolean baseValueRequired, @PathVariable Integer gameId) {
		Optional<GameType> gameType = gameTypeRepository.findById(gameId);
		if (!gameType.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		BaseValueType baseValueType = new BaseValueType();
		baseValueType.setGameType(gameType.get());
		baseValueType.setRequired(baseValueRequired);
		baseValueType.setName(name);
		baseValueType.setDescription(description);
		baseValueType.setExpression(expression);

		baseValueRepository.save(baseValueType);
		return new ResponseEntity(HttpStatus.OK);
	}

}
