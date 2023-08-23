package com.powerledger.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
		System.out.println("#### System Generated Password ####");
		System.out.println("User: miraj");
		System.out.println("Pass: miraj123");
	}

}
