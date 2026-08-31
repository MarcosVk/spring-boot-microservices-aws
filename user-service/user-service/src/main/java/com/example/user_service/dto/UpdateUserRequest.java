package com.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
	
	@NotBlank(message="Name is required")
	@Pattern(
			regexp="^[a-zA-z]+$",
			message = "Name must contain only letters and spaces")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(
		    regexp = "^[2-9]\\d{2}[2-9]\\d{6}$",
		    message = "Phone number must be a valid 10-digit US phone number"
		)
	private int phoneNumber;
	
	@NotBlank(message = "Phone number is required")
	@Size(max=200, message = "Address cannot exceed 200 characters")
	private String address;

}
