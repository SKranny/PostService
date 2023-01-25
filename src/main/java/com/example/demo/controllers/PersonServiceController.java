package com.example.demo.controllers;

import com.example.demo.feign.AuthService;
import com.example.demo.feign.LoginRequest;
import com.example.demo.feign.PersonService;
import dto.userDto.PersonDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Hidden
public class PersonServiceController {
    private final PersonService personService;

    @Operation(summary = "Получение пользователя по email")
    @GetMapping("/{email}")
    public PersonDTO getPersonDTOByEmail(@PathVariable(name = "email") String email) {
        return personService.getPersonDTOByEmail(email);
    }

//@RequiredArgsConstructor
//@RestController
//@Hidden
//public class AuthServiceController {
//
//    private final AuthService authService;
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//        return authService.login(request);
//    }
//
//    @GetMapping("/securityTest")
//    public String hello(){
//        return "securityTest";
//    }
//
}
