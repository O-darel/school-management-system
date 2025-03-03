package com.example.schooldiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SchoolDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolDiscoveryServiceApplication.class, args);
	}

}
