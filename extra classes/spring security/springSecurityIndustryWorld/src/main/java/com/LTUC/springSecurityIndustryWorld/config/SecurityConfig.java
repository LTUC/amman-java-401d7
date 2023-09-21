package com.LTUC.springSecurityIndustryWorld.config;

import com.LTUC.springSecurityIndustryWorld.config.JWTUtil;
import com.LTUC.springSecurityIndustryWorld.config.JwtAuthFilter;
import com.LTUC.springSecurityIndustryWorld.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTH_PATH = "/api/v1/auth/**";
    public static final String USERS_PATH = "/api/v1/nice-worlds/**";
    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH");
    private static final List<String> ALLOWED_HEADERS = Arrays.asList("x-requested-with", "authorization", "Content-Type",
            "Authorization", "credential", "X-XSRF-TOKEN", "X-Refresh-Token", "X-Client-Id", "x-client-id");

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                //.authenticationEntryPoint((request, response, e) -> accessDenied(response))
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .configurationSource(request -> getCorsConfiguration())
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_PATH).permitAll()
                .antMatchers(USERS_PATH).hasAuthority("user")
                .anyRequest().authenticated();
        http.addFilterBefore(new JwtAuthFilter(userDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
    }


    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(ALLOWED_HEADERS);
        corsConfiguration.setAllowedMethods(ALLOWED_METHODS);
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }


//    private void accessDenied(HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.getWriter().write(objectMapper.writeValueAsString(Response.builder()
//                .code(CODE.FORBIDDEN.getId())
//                .message("Access denied")
//                .success(false)
//                .build()));
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
