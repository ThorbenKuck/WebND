package com.github.thorben.webnd.application.controller;

import com.github.thorben.webnd.domain.games.GameType;
import com.github.thorben.webnd.domain.games.GameTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mod")
public class ModeratorController {

	@Inject
	private GameTypeRepository repository;

	@PostMapping("/games")
	public String newGameType(String gameName, String gameDescription) {
		GameType gameType = new GameType(gameDescription, gameName);
		repository.save(gameType);

		return "redirect:/mod";
	}

	@GetMapping
	public String index(Model model) {
		List<GameType> types = new ArrayList<>();
		repository.findAll().forEach(types::add);
		if(!types.isEmpty()) {
			model.addAttribute("gameTypes", types);
		}
		return "mod";
	}

}
