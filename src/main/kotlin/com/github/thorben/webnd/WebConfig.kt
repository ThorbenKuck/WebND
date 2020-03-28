package com.github.thorben.webnd

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurerAdapter() {
	override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
		registry.addResourceHandler(
						"/webjars/**",
						"/img/**",
						"/css/**",
						"/js/**")
				.addResourceLocations(
						"classpath:/META-INF/resources/webjars/",
						"classpath:/static/img/",
						"classpath:/static/css/",
						"classpath:/static/js/")
	}
}