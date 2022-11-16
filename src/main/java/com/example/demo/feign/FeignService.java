package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "FeignService", url = "http://localhost:8080/")
public interface FeignService {
    @PostMapping("/login")
    public LoginResponse login();
}
