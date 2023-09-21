package com.LTUC.springSecurityIndustryWorld.config;

import com.LTUC.springSecurityIndustryWorld.exceptions.UserNotFoundException;
import com.LTUC.springSecurityIndustryWorld.services.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.LTUC.springSecurityIndustryWorld.config.SecurityConfig.AUTH_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    public static final String BEARER = "Bearer ";
    private final CustomUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    public JwtAuthFilter(CustomUserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!request.getServletPath().equals(AUTH_PATH + "/login") && authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                if (username == null) {
                    throw new UserNotFoundException("user not found");
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //we need it to update our security context holder
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
