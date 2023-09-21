package com.LTUC.springSecurityIndustryWorld.services.auth;


import com.LTUC.springSecurityIndustryWorld.bo.auth.AuthenticationResponse;
import com.LTUC.springSecurityIndustryWorld.bo.auth.LogoutRequest;
import com.LTUC.springSecurityIndustryWorld.bo.auth.CreateAuthenticationRequest;
import com.LTUC.springSecurityIndustryWorld.bo.user.CustomUserDetails;
import com.LTUC.springSecurityIndustryWorld.config.JWTUtil;
import com.LTUC.springSecurityIndustryWorld.exceptions.BodyGuardException;
import com.LTUC.springSecurityIndustryWorld.exceptions.UserNotFoundException;
import com.LTUC.springSecurityIndustryWorld.services.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponse login(CreateAuthenticationRequest authenticationRequest) {
        requiredNonNUll(authenticationRequest.getUsername(), "username");
        requiredNonNUll(authenticationRequest.getPassword(), "password");

        String username = authenticationRequest.getUsername().toLowerCase();
        String password = authenticationRequest.getPassword();

        authenticate(username, password);

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String accessToken = jwtUtil.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .id(userDetails.getId())
                .fullName(userDetails.getFullName())
                .email(userDetails.getEmail())
                .userName(userDetails.getUsername())
                .phoneNumber(userDetails.getPhoneNumber())
                .role(userDetails.getRole())
                .address(userDetails.getAddress())
                .token("Bearer " + accessToken)
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        requiredNonNUll(logoutRequest.getToken(), "token");
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect password");
        } catch (AuthenticationServiceException e) {
            throw new UserNotFoundException("Incorrect Username");
        }
    }

    private void requiredNonNUll(Object obj, String name) {
        if (obj == null || obj.toString().isEmpty()) {
            throw new BodyGuardException(name + " can't be empty!");
        }
    }
}
