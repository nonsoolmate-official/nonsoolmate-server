package com.nonsoolmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NonsoolmateBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(NonsoolmateBatchApplication.class, args);
	}
}
