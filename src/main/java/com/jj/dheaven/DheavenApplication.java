package com.jj.dheaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DheavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(DheavenApplication.class, args);
	}

}
