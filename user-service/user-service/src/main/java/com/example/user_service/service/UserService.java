package com.example.user_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.exception.ResourceNotFoundException;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public UserDTO getUser(int id) {
		 User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with ID: "+id));
		 
		 if(user==null) {
			 return null;
		 }
		 
		 UserDTO userDTO=new UserDTO();
		 userDTO.setId(user.getId());
		 userDTO.setName(user.getName());
		 
		 return userDTO;
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

}
