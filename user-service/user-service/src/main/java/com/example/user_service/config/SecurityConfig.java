package com.example.user_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.user_service.filter.RoleAuthFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private RoleAuthFilter authFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.csrf(csfr->csfr.disable())
		.sessionManagement(session->session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth->
		auth.requestMatchers("/actuator/health").permitAll()
		.anyRequest().authenticated())
		.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
		
	}

}
