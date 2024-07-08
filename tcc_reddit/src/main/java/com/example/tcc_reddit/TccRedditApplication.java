package com.example.tcc_reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TccRedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(TccRedditApplication.class, args);
	}

}

