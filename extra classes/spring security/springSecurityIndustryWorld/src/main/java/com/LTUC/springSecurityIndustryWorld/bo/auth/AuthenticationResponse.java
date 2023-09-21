package com.LTUC.springSecurityIndustryWorld.bo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

    private Long id;

    private String fullName;

    private String userName;

    private String email;

    private String phoneNumber;

    private String password;

    private String role;

    private String address;

    private String token;
}
