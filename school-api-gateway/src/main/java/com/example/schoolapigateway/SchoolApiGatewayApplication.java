package com.example.schoolapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SchoolApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApiGatewayApplication.class, args);
	}

}
