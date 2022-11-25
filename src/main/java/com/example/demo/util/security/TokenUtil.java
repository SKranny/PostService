package com.example.demo.util.security;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        return StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ") ? headerAuth.substring(7) : null;
    }
}
