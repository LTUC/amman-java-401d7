package com.LTUC.springSecurityIndustryWorld.services.auth;


import com.LTUC.springSecurityIndustryWorld.bo.auth.AuthenticationResponse;
import com.LTUC.springSecurityIndustryWorld.bo.auth.CreateAuthenticationRequest;
import com.LTUC.springSecurityIndustryWorld.bo.auth.LogoutRequest;

public interface AuthService {
    AuthenticationResponse login(CreateAuthenticationRequest authenticationRequest);
    void logout(LogoutRequest logoutRequest);
}
