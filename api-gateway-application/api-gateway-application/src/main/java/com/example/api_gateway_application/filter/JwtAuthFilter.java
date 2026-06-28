package com.example.api_gateway_application.filter;

import java.io.ObjectInputFilter.Status;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.api_gateway_application.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter{
    @Autowired
    private JwtUtil jwtUtil;
    
    private static final List<String> OPEN_PATHS=List.of("/auth/login", "/auth/register");

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		String path=exchange.getRequest().getURI().getPath();
		System.out.println("Path = " + path);
		
		if(OPEN_PATHS.stream().anyMatch(path::contains)) {
			return chain.filter(exchange);
		}
		
		String header=exchange.getRequest().getHeaders().getFirst("Authorization");
		
		if(header==null || !header.startsWith("Bearer ")){
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		
		String token=header.substring(7);
		
		if(!jwtUtil.validateToken(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		
		String username=jwtUtil.extractUsername(token);
		String role=jwtUtil.extractRole(token);
		
		System.out.println("Forwarding X-User-Name = " + username);
        System.out.println("Forwarding X-User-Role = " + role);
		
		ServerHttpRequest mutatedRequest=exchange.getRequest()
				.mutate()
				.header("X-User-Name", username)
				.header("X-User-Role", role)
				.build();
		
		return chain.filter(exchange.mutate().request(mutatedRequest).build());
	}
    
    

}
