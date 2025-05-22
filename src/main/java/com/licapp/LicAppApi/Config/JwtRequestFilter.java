package com.licapp.LicAppApi.Config;

import com.licapp.LicAppApi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // Define public endpoints that do not require JWT validation
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/user/login",
            "/user/register",
            "/user/test",
            "/user/test1",
            "/user",
            "/plans/test",
            "/blog/test",
            "/testimonials/test",
            "/form/test",
            "/plans",
            "/blog",
            "/testimonials",
            "/form/test1",
            "/form/getform",
            "/form",
            "/form/send"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        logger.info("Processing request: " + requestPath); // Debug log
        if (PUBLIC_PATHS.stream().anyMatch(path -> requestPath.startsWith(path))) {
            logger.info("Skipping JWT validation for public path: " + requestPath);
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("Authorization header: " + authorizationHeader); // Debug log

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                logger.info("Extracted username from JWT: " + username); // Debug log
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
                logger.warn("JWT token is expired: " + e.getMessage());
            } catch (Exception e) {
                logger.error("JWT parsing error: " + e.getMessage());
            }
        } else {
            logger.warn("No valid Authorization header found");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, username)) {
                logger.info("JWT validated successfully for user: " + username);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                logger.warn("JWT validation failed for user: " + username);
            }
        }
        chain.doFilter(request, response);
    }}