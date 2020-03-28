package com.github.thorben.webnd.application.session

import com.github.thorben.webnd.application.stomp.SimpleMessageBroker
import com.github.thorben.webnd.usecase.Dice
import com.github.thorben.webnd.usecase.DiceExpressionEvaluator
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class CampaignListener(
		private val simpleMessageBroker: SimpleMessageBroker,
		private val diceExpressionEvaluator: DiceExpressionEvaluator
) {

	@MessageMapping("/campaigns/{campaign}/roll")
	fun campaignDiceRoll(@DestinationVariable campaign: String, message: List<Dice>) {
		val result = diceExpressionEvaluator.evaluate(message)
		simpleMessageBroker.publish("/topics/campaigns/$campaign/roll", result)
	}
}