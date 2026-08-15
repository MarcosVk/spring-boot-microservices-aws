package com.example.policy_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientConfig {
	
	@Bean
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				
				if(servletRequestAttributes!=null) {
					HttpServletRequest request=servletRequestAttributes.getRequest();
					
					String userName = request.getHeader("X-User-Name");
                    String userRole = request.getHeader("X-User-Role");

                    System.out.println("Feign forwarding X-User-Name: " + userName);
                    System.out.println("Feign forwarding X-User-Role: " + userRole);
                    
                    if(userName!=null) {
                    	template.header("X-User-Name", userName);
                    }
                    if(userRole!=null) {
                    	template.header("X-User-Role", userRole);
                    }
				}
			}
			
		};
	}

}
