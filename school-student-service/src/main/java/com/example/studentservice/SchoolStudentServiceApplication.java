package com.example.studentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SchoolStudentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolStudentServiceApplication.class, args);
	}

}
