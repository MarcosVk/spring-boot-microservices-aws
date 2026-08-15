package com.example.auth_service.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.auth_service.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (request.getRequestURI().equals("/actuator/health")) {
		    filterChain.doFilter(request, response);
		    return;
		}
		
		String authHeader=request.getHeader("Authorization");
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			String token=authHeader.substring(7);
			
			if(jwtUtil.validateToken(token)) {
				String username=jwtUtil.extractUsername(token);
				
				String role=jwtUtil.extractRole(token);
				
				UsernamePasswordAuthenticationToken authenticationToken=
						new UsernamePasswordAuthenticationToken(
								username, null,List.of(new SimpleGrantedAuthority(role)));
				
				authenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
		}
		filterChain.doFilter(request, response);
		
	}
	
	
}
