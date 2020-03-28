package com.github.thorben.webnd.application.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/campaigns")
class CampaignController(
) {
	@GetMapping
	fun landingPage(): String {
		return "campaigns"
	}
	@GetMapping("/{name}")
	fun specificCampaign(@PathVariable name: String): String {
		return "specific_campaign"
	}
}