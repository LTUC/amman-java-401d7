package com.LTUC.springSecurityForTheFirstTime.controllers;

import com.LTUC.springSecurityForTheFirstTime.models.DinosaursLover;
import com.LTUC.springSecurityForTheFirstTime.repositories.DinosaursLoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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


    //==============class 17 ================

    @GetMapping("/test")
    public String getTestPage(Principal p, Model m)
    {
        if (p != null)  // not strictly required if your WebSecurityConfig is correct
        {
            String username = p.getName();
            DinosaursLover dinoUser = dinosaursLoverRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("createdDate", dinoUser.getLocalDate());
        }

        return "/test.html";
    }


    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id)
    {
        if (p != null)  // not strictly required if your WebSecurityConfig is correct
        {
            String username = p.getName();
            DinosaursLover dinoBrowsingUser = dinosaursLoverRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("createdDate", dinoBrowsingUser.getLocalDate());
        }

        DinosaursLover dinoUser = dinosaursLoverRepository.findById(id)
                                    // we use the ResourceNotFoundException that we wrote it down
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id "+id));
        m.addAttribute("dinoUserUsername", dinoUser.getUsername());
        m.addAttribute("dinoUserCreatedDate", dinoUser.getLocalDate());
        m.addAttribute("dinoUserId", dinoUser.getId());
        m.addAttribute("dinoUserManagers", dinoUser.getManagers());
        m.addAttribute("dinoUserEmployees", dinoUser.getEmployees());

        m.addAttribute("testDate", LocalDateTime.now());

        return "/user-info.html";
    }


    //Principal class is used to obtain information about the currently authenticated user
    @PutMapping("/users/{id}")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, RedirectAttributes redir)
    {
        if ((p != null) && (p.getName().equals(username)))//checking whether there is an authenticated user and has a valid session and the username from the session is equaled to the one from the request
        {
            DinosaursLover dinoUser = dinosaursLoverRepository.findById(id).orElseThrow();
            dinoUser.setUsername(username);
            dinoUser.setLocalDate(localDate);
            dinosaursLoverRepository.save(dinoUser);
        }
        else
        {
            //this will allow you to store model attributes temporarily like this message error
            redir.addFlashAttribute("errorMessage", "Cannot edit another user's page!");
        }

        return new RedirectView("/users/" + id);
    }

    // ======== class 18=========
    @PutMapping("/manage-user/{managedDinoId}")
    public RedirectView manageUser(Principal p, @PathVariable Long managedDinoId)
    {
        if (p == null)
        {
            throw new IllegalArgumentException("User must be logged in to manage other users!");
        }

        DinosaursLover browsingDino = dinosaursLoverRepository.findByUsername(p.getName());
        DinosaursLover managedDino = dinosaursLoverRepository.findById(managedDinoId).orElseThrow();

        browsingDino.getEmployees().add(managedDino);
        dinosaursLoverRepository.save(browsingDino);

        return new RedirectView("/users/" + managedDinoId);
    }

    @PutMapping("/employee-of-user/{toBeEmployedDinoId}")
    public RedirectView employUser(Principal p, @PathVariable Long toBeEmployedDinoId)
    {
        if (p == null)
        {
            throw new IllegalArgumentException("User must be logged in to employ other users!");
        }

        DinosaursLover browsingDino = dinosaursLoverRepository.findByUsername(p.getName());
        DinosaursLover managedDino = dinosaursLoverRepository.findById(toBeEmployedDinoId).orElseThrow();

        browsingDino.getEmployees().add(managedDino);
        dinosaursLoverRepository.save(browsingDino);

        return new RedirectView("/users/" + toBeEmployedDinoId);
    }

    @DeleteMapping("/users/{id}")
    public RedirectView deleteUser(@PathVariable Long id)
    {
        DinosaursLover dinoUser = dinosaursLoverRepository.findById(id).orElseThrow();
        dinosaursLoverRepository.delete(dinoUser);

        return new RedirectView("/");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException
    {
        ResourceNotFoundException(String message)
        {
            super(message);
        }
    }
}
