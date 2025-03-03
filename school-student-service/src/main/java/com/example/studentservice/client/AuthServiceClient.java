package com.example.studentservice.client;

import com.example.studentservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "school-auth-service")  // URL of Auth Service
public interface AuthServiceClient {

    @GetMapping("/api/auth/users/email/{email}")
    UserDto getUserByEmail(@PathVariable String email);
}
