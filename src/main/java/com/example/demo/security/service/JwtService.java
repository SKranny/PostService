package com.example.demo.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

public class JwtService {
    @Value("${jwt.secret-code}")
    private String secretKey;

    @Value("${jwt.life-time}")
    private Long lifeTime;

    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        PersonDetails personDetails = (PersonDetails) userDetails;

        claims.put("email", personDetails.getEmail());
        claims.put("roles", personDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(personDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + lifeTime))
                .signWith(HS512, secretKey)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Set<String> getRolesFromToken(String token) {
        return getClaimFromToken(token, (Function<Claims, Set<String>>) claims -> claims.get("roles", Set.class));
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("email", String.class));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        return claimResolver.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
