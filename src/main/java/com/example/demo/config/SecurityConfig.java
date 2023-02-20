package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    @Primary
    public HttpSecurity httpSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(conf -> {
                    conf.antMatchers("/api/v1/post/**").authenticated();
                    conf.antMatchers("/v3/api-docs/**").permitAll();
                    conf.antMatchers("/docs/**").permitAll();
                    conf.anyRequest().authenticated();
                });
    }
}
