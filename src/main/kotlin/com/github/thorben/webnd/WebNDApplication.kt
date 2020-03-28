package com.github.thorben.webnd

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.thorben.webnd.application.SessionStorage
import com.github.thorben.webnd.domain.character.AttributeTypeRepository
import com.github.thorben.webnd.domain.character.BaseValueTypeRepository
import com.github.thorben.webnd.domain.character.DetailTypeRepository
import com.github.thorben.webnd.domain.character.SkillTypeRepository
import com.github.thorben.webnd.domain.games.GameTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.SessionScope

fun main(args: Array<String>) {
	SpringApplication.run(WebNDApplication::class.java, *args)
}

@SpringBootApplication
@Configuration
class WebNDApplication {

	@Bean
	fun mapper(): ObjectMapper {
		return ObjectMapper().registerModule(KotlinModule())
	}
}