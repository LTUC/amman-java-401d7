package com.LTUC.springSecurityIndustryWorld.controllers.auth;



import com.LTUC.springSecurityIndustryWorld.Enums.CODE;
import com.LTUC.springSecurityIndustryWorld.Enums.Roles;
import com.LTUC.springSecurityIndustryWorld.bo.auth.AuthenticationResponse;
import com.LTUC.springSecurityIndustryWorld.bo.auth.LogoutRequest;
import com.LTUC.springSecurityIndustryWorld.bo.response.Response;
import com.LTUC.springSecurityIndustryWorld.bo.auth.CreateAuthenticationRequest;
import com.LTUC.springSecurityIndustryWorld.bo.user.CreateUserRequest;
import com.LTUC.springSecurityIndustryWorld.entity.RoleEntity;
import com.LTUC.springSecurityIndustryWorld.entity.UsersEntity;
import com.LTUC.springSecurityIndustryWorld.exceptions.BodyGuardException;
import com.LTUC.springSecurityIndustryWorld.repositories.RoleRepository;
import com.LTUC.springSecurityIndustryWorld.repositories.UsersRepository;
import com.LTUC.springSecurityIndustryWorld.services.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthService authService;

    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;

    public AuthenticationController(AuthService authService, UsersRepository usersRepository, RoleRepository roleRepository) {
        this.authService = authService;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<Response<AuthenticationResponse>> login(@RequestBody CreateAuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(Response.<AuthenticationResponse>builder()
                .data(authService.login(authenticationRequest))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<?>> logout(@RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return new ResponseEntity<>(Response.builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(),
                HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<Void>> signup(@RequestBody CreateUserRequest createUserRequest) {
        UsersEntity user = fromCreateUserRequest(createUserRequest);
        usersRepository.save(user);
        return new ResponseEntity<>(Response.<Void>builder()
                .data(null)
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(), HttpStatus.OK);
    }

    public  UsersEntity fromCreateUserRequest(CreateUserRequest createUserRequest) {
        RoleEntity role= roleRepository.findRoleEntityByTitle(Roles.user.name())
                .orElseThrow(() -> new BodyGuardException("no Roles Found"));
        UsersEntity user = new UsersEntity();
        //user.setId(7L);
        user.setUsername(createUserRequest.getUserName());
        user.setPassword(createUserRequest.getPassword());
        user.setAddress(createUserRequest.getAddress());
        user.setEmail(createUserRequest.getEmail());
        user.setFullName(createUserRequest.getFullName());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setRole(role);
        // Set other fields as needed
        return user;
    }

}
