package com.github.thorben.webnd;

import com.github.thorben.webnd.application.SessionStorage;
import com.github.thorben.webnd.application.tech.MessageBroker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class WebNDApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebNDApplication.class, args);
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public MessageBroker messageBroker() {
		return new MessageBroker();
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public SessionStorage sessionStorage() {
		return new SessionStorage();
	}

}
