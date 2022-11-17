package com.example.demo;

import com.example.demo.feign.FeignService;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

@EnableFeignClients(clients = {FeignService.class})
@SpringBootApplication
@RestController
public class PostService {

	@GetMapping("/")
	String home() {
		return "Spring is here!";
	}

	public static void main(String[] args) {
		SpringApplication.run(PostService.class, args);
	}
}