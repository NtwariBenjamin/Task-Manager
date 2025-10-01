package com.benjamin.authservice.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Value("${auth.trusted-services:}")
    private List<String> trustedServices; // configured in application.yml

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            try {
                String subject = jwtService.extractUsername(jwtToken);
                Claims claims = jwtService.extractAllClaims(jwtToken);

                if (isTrustedService(subject, claims)) {
                    authenticateService(subject);
                } else {
                    authenticateUser(subject, jwtToken);
                }

            } catch (Exception e) {
                logger.error("Invalid JWT Token", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // stop processing the request
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTrustedService(String subject, Claims claims) {
        return trustedServices.contains(subject) && "SERVICE".equals(claims.get("role"));
    }

    private void authenticateService(String serviceName) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(serviceName, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void authenticateUser(String username, String jwtToken) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (jwtService.validateToken(jwtToken, userDetails)) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}