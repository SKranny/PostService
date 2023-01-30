package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import security.TokenAuthentication;
import java.util.List;

@FeignClient("friend-service/api/v1/friends")
public interface FriendService {
    @GetMapping("/ids")
    List<Long> getFriendId(TokenAuthentication authentication);
}

