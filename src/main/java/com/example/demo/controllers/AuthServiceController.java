package com.example.demo.controllers;


import com.example.demo.feign.AuthService;
import com.example.demo.feign.LoginRequest;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Hidden
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
