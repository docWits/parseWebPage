package com.romanyuta.ParserWebPage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ParserWebPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParserWebPageApplication.class, args);
	}

}
