package com.example.api_gateway_application.util;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}
	public String extractRole(String token) {
		return (String) getClaims(token).get("role");
	}
	
	public boolean validateToken(String token) {
		try {
			getClaims(token);
		return true;
		}catch(Exception e) {
			return false;
		}
	}
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
		.setSigningKey(getSigningKey())
		.build()
		.parseClaimsJws(token)
		.getBody();
	}

}
