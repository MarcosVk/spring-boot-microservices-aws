package com.example.auth_service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducer {
	private static final String TOPIC="user-registered";
	
	@Autowired
	private KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;
	
	public void publishUserRegistered(UserRegisteredEvent event) {
		System.out.println("Publishing event to Kafka: " + event);
		kafkaTemplate.send(TOPIC,event);
	}

}
