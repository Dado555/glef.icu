package com.sbnz.gleficu.util;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String headerString;

    @Value("${jwt.token.prefix}")
    public String tokenPrefix;

    private final JwtUtil tokenUtil;
    private final UserDetailsService userDetailsService;

    protected JwtFilter(JwtUtil tokenUtil, UserDetailsService userDetailsService) {
        this.tokenUtil = tokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String jwt = this.getJwtFromRequestHeader(request);
        if (jwt != null) {
            this.validateToken(jwt);
        }

        filterChain.doFilter(request, response);
    }

    protected String getJwtFromRequestHeader(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(headerString);
        if (authorizationHeader == null)
            return null;
        else if (authorizationHeader.startsWith(tokenPrefix))
            return authorizationHeader.replace(tokenPrefix, "");
        return null;
    }

    protected void validateToken(String jwt) {
        try {
            String username = tokenUtil.extractUsernameFromToken(jwt);


            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (Boolean.TRUE.equals(tokenUtil.validateToken(jwt, userDetails))) {
                TokenBasedAuth authenticationToken = new TokenBasedAuth(userDetails);
                authenticationToken.setToken(jwt);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (IllegalArgumentException |
                SignatureException |
                ExpiredJwtException |
                MalformedJwtException |
                UnsupportedJwtException |
                UsernameNotFoundException |
                InvalidCookieException ignored) {
            log.error(ignored.getMessage());
        }
    }
}
