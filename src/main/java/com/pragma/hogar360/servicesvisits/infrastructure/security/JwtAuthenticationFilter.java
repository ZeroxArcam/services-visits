package com.pragma.hogar360.servicesvisits.infrastructure.security;

import com.pragma.hogar360.servicesvisits.infrastructure.exceptions.UnauthorizedException;
import com.pragma.hogar360.servicesvisits.infrastructure.exceptionshandler.ExceptionConstants;
import com.pragma.hogar360.servicesvisits.infrastructure.utils.InfrastructureConstants;
import com.pragma.hogar360.servicesvisits.infrastructure.utils.JwtErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    public static final String USER_ID_REQUEST_ATTRIBUTE = "userId";
    public static final String USER_EMAIL_REQUEST_ATTRIBUTE = "email";

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

        try {
            final List<SimpleGrantedAuthority> roles = jwtService.extractRoles(jwt);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtService.isTokenValid(jwt)) {
                    Long userId = jwtService.extractUserIdFromToken(jwt);
                    String email = jwtService.extractEmailFromToken(jwt);

                    if (userId == null || email == null) {
                        throw new RuntimeException("Faltan datos del token");
                    }

                    UserDetails userDetails = new User(String.valueOf(userId), "", roles);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    request.setAttribute(USER_ID_REQUEST_ATTRIBUTE, userId);
                    request.setAttribute(USER_EMAIL_REQUEST_ATTRIBUTE, email);
                } else {
                    throw new UnauthorizedException(ExceptionConstants.INVALID_TOKEN_ERROR_CODE,ExceptionConstants.JWT_EXPIRED_MESSAGE_EN);

                }
            }

            filterChain.doFilter(request, response);

        }catch (Exception e) {
            log.error("❌ Error en JwtAuthenticationFilter: {}", e.getMessage());

            String errorMessage = e.getMessage();
            String errorCode = "H360-401-000"; // Default error code

            if (e instanceof UnauthorizedException unauthorizedEx) {
                errorMessage = unauthorizedEx.getMessage();
                errorCode = unauthorizedEx.getErrorCode();
            }

            // Aquí usamos la plantilla con el mensaje
            String formattedMessage = String.format(InfrastructureConstants.UNAUTHORIZED_MESSAGE_TEMPLATE, errorMessage);

            JwtErrorResponse errorResponse = new JwtErrorResponse(
                    formattedMessage,
                    errorCode
            );

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        }


    }

}