package com.example.schoolapigateway.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/students")
    public ResponseEntity<String> studentFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Student Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/auth")
    public ResponseEntity<String> authFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Auth Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/config")
    public ResponseEntity<String> configFallback (){
        return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                "Config Service is currently unavailable. Please try again later."
        );
    }
}
