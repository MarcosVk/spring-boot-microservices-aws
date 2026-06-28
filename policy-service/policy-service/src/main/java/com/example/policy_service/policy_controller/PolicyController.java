package com.example.policy_service.policy_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy_service.feign.UserClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/policies")
public class PolicyController {
	@Autowired
	private UserClient userClient;
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/{id}/user")
	@CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
	public String getPolicy(@PathVariable int id) {
		String user=userClient.getUser(id);
		 return "Policy " + id + " belongs to " + user;
		}
	
	private String fallbackUser(int id,Throwable ex) {
		return "User service temporarily unavailable"+ex.getMessage();
	}

}
