package com.example.demo;

import com.example.demo.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
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

	@GetMapping("/email")
	String email() {
		return feignService.getEmail();
	}

	public static void main(String[] args) {
		SpringApplication.run(PostService.class, args);
	}
}