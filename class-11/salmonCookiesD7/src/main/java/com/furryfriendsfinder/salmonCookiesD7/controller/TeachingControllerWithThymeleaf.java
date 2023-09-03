package com.furryfriendsfinder.salmonCookiesD7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeachingControllerWithThymeleaf {

    @GetMapping("/names")
    public String renderAPage(Model viewName){
        viewName.addAttribute("name","Thaer");
        return "teaching.html";
    }
}
