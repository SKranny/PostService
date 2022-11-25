package com.example.demo.security.filter;

import com.example.demo.security.service.JwtService;
import com.example.demo.util.security.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtPerRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = TokenUtil.parseToken(request);
        String userName = null;

        if (Optional.ofNullable(token).isPresent()) {
            try {
                userName = jwtService.getUserNameFromToken(token);
            }catch (ExpiredJwtException e){
                logger.debug("The token is expired");
            }

            UsernamePasswordAuthenticationToken details = new UsernamePasswordAuthenticationToken(userName, null, jwtService.getRolesFromToken(token).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(details);
        }

        filterChain.doFilter(request, response);
    }
}
