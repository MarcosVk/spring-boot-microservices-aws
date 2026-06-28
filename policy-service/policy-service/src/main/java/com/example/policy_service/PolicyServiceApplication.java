package com.example.policy_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableFeignClients
@SpringBootApplication
@EnableMethodSecurity
public class PolicyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyServiceApplication.class, args);
	}

}
