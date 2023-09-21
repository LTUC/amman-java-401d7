package com.LTUC.springSecurityIndustryWorld.bo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest {

    private  String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String userName;

    private String password;
}
