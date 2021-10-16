package com.sander.fantastic_food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class FantasticFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasticFoodApplication.class, args);
		System.out.println("FantasticFood started");
	}

}
