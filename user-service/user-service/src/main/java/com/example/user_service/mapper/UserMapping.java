package com.example.user_service.mapper;

import com.example.user_service.dto.UserResponse;
import com.example.user_service.model.User;

public class UserMapping {
	
	public UserResponse userToUserRes(User user) {
		UserResponse res=new UserResponse();
		res.setId(user.getId());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setPhoneNumber(user.getPhoneNumber());
		res.setAddress(user.getAddress());
		return res;
		
	}

}
