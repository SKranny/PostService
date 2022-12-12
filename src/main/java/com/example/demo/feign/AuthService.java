package com.example.demo.feign;


import dto.authDto.auth.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient("auth-service/api/v1/auth")
public interface AuthService {
    @PostMapping("/login")
    String login(LoginRequest request);
}
