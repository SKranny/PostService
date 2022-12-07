package com.example.demo.controller;


import com.example.demo.feign.AuthService;
import dto.authDto.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthServiceController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/securityTest")
    public String hello(){
        return "securityTest";
    }

}
