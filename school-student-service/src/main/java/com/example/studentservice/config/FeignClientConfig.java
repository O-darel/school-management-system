package com.example.studentservice.config;

import com.example.studentservice.client.FeignClientInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (authentication != null && authentication.getCredentials() instanceof String token) {
//                requestTemplate.header("Authorization", "Bearer " + token);
//            }
//        };
        return  new FeignClientInterceptor();
    }
}
