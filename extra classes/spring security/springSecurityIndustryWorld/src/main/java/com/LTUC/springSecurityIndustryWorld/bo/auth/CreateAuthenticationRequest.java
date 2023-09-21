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
public class CreateAuthenticationRequest {

    private String username;

    private String password;
}
