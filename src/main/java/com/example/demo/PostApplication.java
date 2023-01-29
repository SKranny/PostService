package com.example.demo;

import feignClient.EnableFeignClient;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import security.EnableMicroserviceSecurity;

@EnableFeignClient
@EnableEurekaClient
@SpringBootApplication
@EnableMicroserviceSecurity
public class PostApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostApplication.class, args);
	}
}