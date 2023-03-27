package com.example.projectCommon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class projectCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(projectCommonApplication.class, args);
	}

}
