package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:path.properties")
public class MatchWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchWebsiteApplication.class, args);
	}

}
