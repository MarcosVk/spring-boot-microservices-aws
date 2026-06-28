package com.example.auth_service.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String SECRET;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	public String generateToken(String userName,String role) {
		return Jwts.builder()
				.setSubject(userName)
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.compact();
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
	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

}
