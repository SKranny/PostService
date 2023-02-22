package com.example.demo.feign;

import dto.userDto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Set;

@FeignClient("person-service/api/v1/account")
public interface PersonService {
    @GetMapping("/{email}")
    PersonDTO getPersonDTOByEmail(@PathVariable(name = "email") String email);

    @GetMapping("/searchByName")
    Set<PersonDTO> searchAccountsByName(@RequestParam(value = "userName", required = false) String userName);
}
