package com.LTUC.springSecurityIndustryWorld.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/nice-worlds")
public class NiceWorldsControllers {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from our API");
    }

    @GetMapping("/bye")
    public ResponseEntity<String> sayGoodBye(){
        return ResponseEntity.ok("see u later");
    }
}
