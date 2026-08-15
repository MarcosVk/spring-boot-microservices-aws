package com.example.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.LoginResponse;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.RegisterResponse;
import com.example.auth_service.event.UserEventProducer;
import com.example.auth_service.event.UserRegisteredEvent;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.util.JwtUtil;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private JwtUtil jwtUtil;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserEventProducer userEventProducer;
	
	public LoginResponse login(LoginRequest request) {
		User user=userRepository.findByUserName(request.getUserName())
				.orElseThrow(()->new RuntimeException("User not found"));
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}
		
		String token=jwtUtil.generateToken(user.getUserName(), user.getRole());
		return new LoginResponse(token, user.getUserName(), user.getRole());
		
	}
	
	public RegisterResponse register(RegisterRequest registerRequest) {
		if(userRepository.existsByUserName(registerRequest.getUserName())) {
			throw new RuntimeException("User already exists");
		}
		
		User user=new User();
		user.setUserName(registerRequest.getUserName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setRole(registerRequest.getRole()!=null? registerRequest.getRole():"ROLE_USER");
		
		User savedUser=userRepository.save(user);
		
		UserRegisteredEvent event=new UserRegisteredEvent(
				savedUser.getUserName(),savedUser.getRole(),"New user registered");
		userEventProducer.publishUserRegistered(event);
		
		return new RegisterResponse("User registered successfully", savedUser.getUserName(), savedUser.getRole());
	}

}
