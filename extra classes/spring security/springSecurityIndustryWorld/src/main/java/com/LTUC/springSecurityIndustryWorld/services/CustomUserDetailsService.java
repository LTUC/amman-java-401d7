package com.LTUC.springSecurityIndustryWorld.services;


import com.LTUC.springSecurityIndustryWorld.exceptions.UserNotFoundException;
import com.LTUC.springSecurityIndustryWorld.entity.UsersEntity;
import com.LTUC.springSecurityIndustryWorld.repositories.UsersRepository;
import com.LTUC.springSecurityIndustryWorld.bo.user.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buildCustomUserDetailsOfUsername(username);
    }

    private CustomUserDetails buildCustomUserDetailsOfUsername(String username) {
        UsersEntity user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Incorrect Username"));

        return CustomUserDetails.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .userName(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .role(user.getRole().getTitle().name())
                .address(user.getAddress())
                .build();
    }
}
