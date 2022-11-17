package com.example.demo.controller;


import com.example.demo.feign.FeignService;
import com.example.demo.feign.LoginRequest;
import com.example.demo.feign.LoginResponse;
import com.example.demo.feign.LogoutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AuthServiceController {

    @Autowired
    private FeignService feignService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return feignService.login(request);
    }

    @PostMapping("/logout")
    public LogoutResponse logout(HttpServletRequest request){
        return feignService.logout(request);
    }
}
