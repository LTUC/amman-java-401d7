package com.LTUCD7.salmonCookies2.controllers.salmonCookies;

import com.LTUCD7.salmonCookies2.models.SalmonCookiesStore;
import com.LTUCD7.salmonCookies2.repositories.SalmonCookiesStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class SalmonCookiesController {

    @Autowired
    SalmonCookiesStoreRepository salmonCookiesStoreRepository;

    @GetMapping("/")
    public String getSalmonCookiesStores(Model m)
    {
        List<SalmonCookiesStore> stores = salmonCookiesStoreRepository.findAll();
        m.addAttribute("stores", stores);

        return "salmon-cookies-stores.html";
    }

    @PostMapping("/")
    public RedirectView createSalmonCookieStore(String name, Long averageCookiesPerDay){
        SalmonCookiesStore newStore = new SalmonCookiesStore(name,averageCookiesPerDay);
        salmonCookiesStoreRepository.save(newStore);

        return new RedirectView("/");
    }

    @PutMapping("/update/{salmonCookieId}")
    public RedirectView updateSalmonCookieStore(@PathVariable Long salmonCookieId, String newName, Long averageCookiesPerDay) {
        SalmonCookiesStore salmonCookiesStore=salmonCookiesStoreRepository.findById(salmonCookieId).get();
        salmonCookiesStore.setName(newName);
        salmonCookiesStore.setAverageCookiesPerDay(averageCookiesPerDay);
        salmonCookiesStoreRepository.save(salmonCookiesStore);
        return new RedirectView("/");
    }

    @DeleteMapping("/delete/{salmonCookieId}")
    public RedirectView deleteSalmonCookieStore(@PathVariable Long salmonCookieId){
        salmonCookiesStoreRepository.deleteById(salmonCookieId);

        return  new RedirectView("/");
    }
}
