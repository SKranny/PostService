package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@FeignClient(value = "AuthService", url = "http://localhost:8081/api/v1/auth/")
public interface FeignService {
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request);

    @PostMapping("/logout")
    public LogoutResponse logout(HttpServletRequest request);
}
