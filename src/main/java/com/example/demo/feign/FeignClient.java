package com.example.demo.feign;

@org.springframework.cloud.openfeign.FeignClient(value = "FeignClient", url = "")
public interface FeignClient {
}
