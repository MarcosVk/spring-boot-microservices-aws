package com.example.user_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.user_service.dto.UpdateUserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.exception.ResourceNotFoundException;
import com.example.user_service.mapper.UserMapping;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final UserMapping userMapping;
	
	public UserService(UserRepository userRepository,UserMapping userMapping) {
		this.userRepository=userRepository;
		this.userMapping=userMapping;
	}
	
	public UserResponse getUser(int id) {
		 User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with ID: "+id));
		 
		 return userMapping.userToUserRes(user); 
	}
	
	public UserResponse saveUser(UpdateUserRequest userReq) {
		User user=new User();
		user.setName(userReq.getName());
		user.setEmail(userReq.getEmail());;
		user.setPhoneNumber(userReq.getPhoneNumber());
		user.setAddress(userReq.getAddress());
		userRepository.save(user);
		return userMapping.userToUserRes(user); 
	}
	
	public List<UserResponse> getAllUsers(){
		return userRepository.findAll()
	            .stream()
	            .map(userMapping::userToUserRes)
	            .toList();
	}
	
	public UserResponse upateUser(int id,UpdateUserRequest userReq) {
		
		User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with ID: "+id));
		 
		user.setName(userReq.getName());
	    user.setEmail(userReq.getEmail());
	    user.setPhoneNumber(userReq.getPhoneNumber());
	    user.setAddress(userReq.getAddress());
		userRepository.save(user);
		return userMapping.userToUserRes(user); 
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

}
