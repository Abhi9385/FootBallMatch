package com.eminence.footballmatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FootballmatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballmatchApplication.class, args);
	}

}
