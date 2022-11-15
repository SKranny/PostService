package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "FeignService", url = "")
public interface FeignService {
    @GetMapping("/email")
    String getEmail();
}
