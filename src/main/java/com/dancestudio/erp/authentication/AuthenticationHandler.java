package com.dancestudio.erp.authentication;

import com.dancestudio.erp.exception.MembershipExpiredException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationHandler extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public AuthenticationHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String formAuthorizationHeader = request.getHeader("Form-Authorization");
        String requestURI = request.getRequestURI();

        List<String> excludedPaths = List.of("/swagger-ui", "/refreshToken", "/verify", "/plans/getAll", "api-docs", "/v3/api-docs", "/swagger-ui.html", "/users/login", "/password/reset", "/studios/add", "/studentActivities/invoice", "/booking/invoice");
        if (excludedPaths.stream().anyMatch(requestURI::contains)) {
            filterChain.doFilter(request, response);
            return;
        }

        String secret = "6mB5xkS09TuhPjG6zTiG+PDUrcyBuZoVdBBvHxITtXQ";
        if (formAuthorizationHeader != null && formAuthorizationHeader.equals(secret)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid");
            return;
        }

        String token = authorizationHeader.substring(7);

        try {
            Claims claims;
            if (requestURI.contains("/super-admin") || requestURI.contains("/plans")) {
                claims = jwtUtil.extractAllClaims(token);
            } else {
                claims = jwtUtil.validateAndParseClaims(token);
            }
            String email = claims.getSubject();
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, null, List.of())
            );

        } catch (MembershipExpiredException e) {
            response.sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, "Membership is expired");
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
