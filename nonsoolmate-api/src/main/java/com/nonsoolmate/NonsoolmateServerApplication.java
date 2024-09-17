package com.nonsoolmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NonsoolmateServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NonsoolmateServerApplication.class, args);
	}
}
