package com.LTUC.authDemoFinalVersion.controller;

import com.LTUC.authDemoFinalVersion.Repositories.SiteUserRepository;
import com.LTUC.authDemoFinalVersion.models.SiteUser;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    @Autowired
    SiteUserRepository siteUserRepository;

    @GetMapping("/login")
    public String getLoginPage(){
        return "/login.html";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
        return "/signup.html";
    }

    @PostMapping("/signup")
    public RedirectView signUpUser(String username, String password){

        //String plainTextPassword=password;
        //SiteUser siteUser = new SiteUser(username,password);
        String hashedPassword= BCrypt.hashpw(password, BCrypt.gensalt(12));
        SiteUser siteUser = new SiteUser(username,hashedPassword);

        siteUserRepository.save(siteUser);
        return new RedirectView("login");
    }

    @PostMapping("/login")
    public RedirectView logInUser(String username, String password){
       SiteUser userFromDb=siteUserRepository.findByUsername(username);

       // old way using plainText
//       if((userFromDb == null)
//           || !(userFromDb.getPassword().equals(password))){
//           return new RedirectView("/login");
//       }

        if((userFromDb == null)
                || (!BCrypt.checkpw(password, userFromDb.getPassword())))
        {

            return new RedirectView("/login");
        }

       return new RedirectView("/");
    }

    @PostMapping("/loginWithSecret")
    public RedirectView logInUSerWithSecret(HttpServletRequest request, String username, String password){
        SiteUser userFromDb=siteUserRepository.findByUsername(username);

        if((userFromDb == null)
                || (!BCrypt.checkpw(password, userFromDb.getPassword())))
        {

            return new RedirectView("/loginWithSecret");
        }
        HttpSession session= request.getSession();
        session.setAttribute("username", username);

        return new RedirectView("/withSecret");
    }

    @PostMapping("logoutWithSecret")
    public RedirectView logOutUserWithSecret(HttpServletRequest request){

        HttpSession session= request.getSession();
        session.invalidate();

        return new RedirectView("/loginWithSecret");
    }

    @GetMapping("/loginWithSecret")
    public String getLoginPageWithSecret(){
        return "/loginWithSecret.html";
    }
}
