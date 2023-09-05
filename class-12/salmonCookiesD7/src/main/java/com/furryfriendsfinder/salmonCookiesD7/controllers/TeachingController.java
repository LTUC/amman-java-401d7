package com.furryfriendsfinder.salmonCookiesD7.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TeachingController {

    @ResponseBody
    @GetMapping("/hello-world")
    public String sayHello(){
        return "Hello world";
    }
}
