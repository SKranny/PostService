package com.example.demo;

import com.example.demo.feign.AuthService;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients(clients = {AuthService.class})
@SpringBootApplication
public class PostService {

	public static void main(String[] args) {
		SpringApplication.run(PostService.class, args);
	}
}