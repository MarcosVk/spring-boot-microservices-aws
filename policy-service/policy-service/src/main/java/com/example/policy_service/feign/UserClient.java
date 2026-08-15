package com.example.policy_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.policy_service.config.FeignClientConfig;
import com.example.policy_service.dto.UserDTO;

@FeignClient(name="USER-SERVICE",configuration=FeignClientConfig.class)
public interface UserClient {
	@GetMapping("/users/{id}")
	UserDTO getUser(@PathVariable int id);

}
