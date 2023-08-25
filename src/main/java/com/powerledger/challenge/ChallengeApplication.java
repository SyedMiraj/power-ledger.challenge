package com.powerledger.challenge;

import com.powerledger.challenge.domains.UserDomain;
import com.powerledger.challenge.jwt.JwtUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {

	private final JwtUserDetailsService userDetailsService;

	public ChallengeApplication(JwtUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserDomain user = new UserDomain();
		user.setUsername("test");
		user.setPassword("123");
		userDetailsService.save(user);
	}
}
