package com.example.user_service.user_controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public User CreateUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/{id}")
	public UserDTO getUser(@PathVariable int id) {
		return userService.getUser(id);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id){
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted");
	}

}
