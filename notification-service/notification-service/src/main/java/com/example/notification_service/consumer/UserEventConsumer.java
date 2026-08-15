package com.example.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.notification_service.dto.UserRegisteredEvent;

@Component
public class UserEventConsumer {
	
	@KafkaListener(
			topics="user-registered",
			groupId="notification-group"
			)
	
	public void handleUserRegistered(UserRegisteredEvent event) {
		System.out.println("=== NOTIFICATION SERVICE ===");
        System.out.println("New user registered: " + event.getUserName());
        System.out.println("Role: " + event.getRole());
        System.out.println("Sending welcome email to: " + event.getUserName());
        System.out.println("============================");
	}
}
