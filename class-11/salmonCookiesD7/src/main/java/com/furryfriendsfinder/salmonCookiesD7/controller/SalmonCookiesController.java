package com.furryfriendsfinder.salmonCookiesD7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SalmonCookiesController {

    @GetMapping("/cookie/{cookieName}")
    public String renderAPageWithStyleAndPathVariable(Model m , @PathVariable String cookieName){
        System.out.println("print cookie name: "+cookieName);
        String name="thaer";

        m.addAttribute("name", name.toUpperCase());

        return "teaching.html";
    }


    @GetMapping("/cookie")
    public String renderAPageWithStyleAndQueryParam(Model m , @RequestParam String cookieName){
        System.out.println("print cookie name: "+cookieName);
        m.addAttribute("name", "thaer");

        return "teaching.html";
    }
}
