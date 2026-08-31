package com.example.user_service.user_controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.UpdateUserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping
	public ResponseEntity<UserResponse> CreateUser(@RequestBody UpdateUserRequest user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable int id) {
		return ResponseEntity.ok(userService.getUser(id));
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<UserResponse> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER)")
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable int id,@RequestBody  UpdateUserRequest user){
		return ResponseEntity.ok(userService.upateUser(id,user));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id){
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted");
	}

}
