package com.example.demo;

@org.springframework.cloud.openfeign.FeignClient(value = "FeignClient", url = "")
public interface FeignClient {
}
