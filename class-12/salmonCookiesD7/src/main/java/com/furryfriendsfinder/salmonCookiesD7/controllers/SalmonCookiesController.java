package com.furryfriendsfinder.salmonCookiesD7.controllers;

import com.furryfriendsfinder.salmonCookiesD7.models.SalmonCookiesStore;
import com.furryfriendsfinder.salmonCookiesD7.repositores.SalmonCookiesStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.List;

@Controller()
public class SalmonCookiesController {

//    @GetMapping("/cookie/{cookieName}")
//    public String renderAPageWithStyleAndPathVariable(Model m , @PathVariable String cookieName){
//        System.out.println("print cookie name: "+cookieName);
//        String name="thaer";
//
//        m.addAttribute("name", name.toUpperCase());
//
//        return "teaching.html";
//    }
//
//
//    @GetMapping("/cookie")
//    public String renderAPageWithStyleAndQueryParam(Model m , @RequestParam String cookieName){
//        System.out.println("print cookie name: "+cookieName);
//        m.addAttribute("name", "thaer");
//
//        return "teaching.html";
//    }

    @Autowired
    SalmonCookiesStoreRepository salmonCookiesStoreRepository;
    final LocalDateTime now = LocalDateTime.now();
    @GetMapping("/")
    public String getSalmonCookiesStores(Model m)
    {
        List<SalmonCookiesStore> stores = salmonCookiesStoreRepository.findAll();
        m.addAttribute("stores", stores);

        //SalmonCookiesStore salmonCookiesStore = salmonCookiesStoreRepository.findByName("Best Salmon Cookies");

        return "salmon-cookies-stores.html";
    }

    @PostMapping("/create-salmon")
    public RedirectView createSalmonCookieStore(String name)
    {
        SalmonCookiesStore newStore = new SalmonCookiesStore(name, now);
        System.out.println("name: "+name);
        salmonCookiesStoreRepository.save(newStore);

        return new RedirectView("/");
    }

    @DeleteMapping("/delete/{salmonCookieId}")
    public RedirectView deleteSalmonCookieStore(@PathVariable Long salmonCookieId)
    {
        // TODO: Implement me as a stretch goal if you want
        salmonCookiesStoreRepository.deleteById(salmonCookieId);
        return new RedirectView("/");
    }
}
