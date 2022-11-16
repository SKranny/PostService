package com.example.demo;

import com.example.demo.feign.FeignService;
import com.example.demo.feign.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

@EnableFeignClients
@SpringBootApplication
@RestController
public class PostService {
	@Autowired
	private FeignService feignService;

	@GetMapping("/")
	String home() {
		return "Spring is here!";
	}

	@PostMapping("/login")
	public LoginResponse login() {
		return feignService.login();
	}

	public static void main(String[] args) {
		SpringApplication.run(PostService.class, args);
	}
}