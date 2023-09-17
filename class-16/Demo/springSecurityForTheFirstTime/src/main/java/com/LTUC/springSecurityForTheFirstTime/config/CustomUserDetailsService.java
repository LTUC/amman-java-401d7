package com.LTUC.springSecurityForTheFirstTime.config;

import com.LTUC.springSecurityForTheFirstTime.models.DinosaursLover;
import com.LTUC.springSecurityForTheFirstTime.repositories.DinosaursLoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    DinosaursLoverRepository dinosaursLoverRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DinosaursLover dinosaursLover= dinosaursLoverRepository.findByUsername(username);

        if(dinosaursLover == null){
            System.out.println("User not found "+ username);
            throw new UsernameNotFoundException("user"+ username+ " was not found in the db");
        }
        System.out.println("Found User: "+dinosaursLover.getUsername());
        return dinosaursLover;
    }
}
