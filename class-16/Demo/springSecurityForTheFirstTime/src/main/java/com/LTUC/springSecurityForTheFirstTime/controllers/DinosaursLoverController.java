package com.LTUC.springSecurityForTheFirstTime.controllers;

import com.LTUC.springSecurityForTheFirstTime.models.DinosaursLover;
import com.LTUC.springSecurityForTheFirstTime.repositories.DinosaursLoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class DinosaursLoverController {

    @Autowired
    DinosaursLoverRepository dinosaursLoverRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
         return "signup.html";
    }

    @GetMapping("/logout")
    public String getLogoutPage(){
        return "index.html";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password){
        DinosaursLover dinosaursLover = new DinosaursLover();
        dinosaursLover.setUsername(username);
        dinosaursLover.setLocalDate(LocalDate.now());

        String encryptedPassword = passwordEncoder.encode(password);
        dinosaursLover.setPassword(encryptedPassword);

        dinosaursLoverRepository.save(dinosaursLover);
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    @GetMapping("/")
    public String getHomePage(Principal p, Model m){

        if(p != null){
            String username = p.getName();
            DinosaursLover dinosaursLover= dinosaursLoverRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("createdDate", dinosaursLover.getLocalDate());
        }

        return "index.html";
    }
    public void authWithHttpServletRequest(String username, String password){

        try {
            request.login(username, password);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }
}
