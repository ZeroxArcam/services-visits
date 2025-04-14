package com.pragma.hogar360.servicesvisits.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    public static final String USER_ID_REQUEST_ATTRIBUTE = "userId";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("🔒 No JWT token found in Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        final List<org.springframework.security.core.authority.SimpleGrantedAuthority> roles = jwtService.extractRoles(jwt);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.isTokenValid(jwt)) {
                final Long userId = jwtService.extractUserIdFromToken(jwt);
                String username = null;
                if (userId != null) {
                    username = String.valueOf(userId);
                    log.info("👤 User ID extracted from JWT: {}", username);
                    log.info("🛡️ Roles extracted from JWT: {}", roles);

                    UserDetails userDetails = new User(username, "", roles);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.info("✅ Authentication set in SecurityContextHolder for user ID: {}", username);

                    request.setAttribute(USER_ID_REQUEST_ATTRIBUTE, userId);
                    log.debug("🔑 User ID extracted from token and set as request attribute: {}", userId);
                } else {
                    log.warn("⚠️ Could not extract user ID from token.");
                }

            } else {
                log.warn("⚠️ Invalid JWT token");
            }
        }

        filterChain.doFilter(request, response);
    }
}