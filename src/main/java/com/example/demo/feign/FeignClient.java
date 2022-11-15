package com.example.demo.feign;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.cloud.openfeign.FeignClient(value = "FeignClient", url = "")
public interface FeignClient {
}
