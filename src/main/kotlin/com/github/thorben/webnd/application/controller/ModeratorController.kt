package com.github.thorben.webnd.application.controller

import com.github.thorben.webnd.domain.games.GameType
import com.github.thorben.webnd.domain.games.GameTypeRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*
import java.util.function.Consumer
import javax.inject.Inject

@Controller
@RequestMapping("/mod")
class ModeratorController(
		private val repository: GameTypeRepository
) {
	@PostMapping("/games")
	fun newGameType(gameName: String, gameDescription: String): String {
		val gameType = GameType(gameDescription, gameName)
		repository.save(gameType)
		return "redirect:/mod"
	}

	@GetMapping
	fun index(model: Model): String {
		val types: MutableList<GameType> = ArrayList()
		repository.findAll().forEach { types.add(it) }
		if (types.isNotEmpty()) {
			model.addAttribute("gameTypes", types)
		}
		return "mod"
	}
}