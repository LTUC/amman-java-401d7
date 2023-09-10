package com.LTUCD7.salmonCookies2.controllers.salmonCookies;

import com.LTUCD7.salmonCookies2.exceptions.CookieStoreNotFoundException;
import com.LTUCD7.salmonCookies2.models.Employee;
import com.LTUCD7.salmonCookies2.models.SalmonCookiesStore;
import com.LTUCD7.salmonCookies2.repositories.EmployeeRepository;
import com.LTUCD7.salmonCookies2.repositories.SalmonCookiesStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EmployeeController {

    @Autowired
    SalmonCookiesStoreRepository salmonCookiesStoreRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/add-employee")
    public RedirectView addEmployee(String name, Long pricePerHour, Long storeId){
       SalmonCookiesStore salmonCookiesStore= salmonCookiesStoreRepository
                .findById(storeId)
                .orElseThrow(() -> new CookieStoreNotFoundException("Could not find cookie store for this employee in db!"));

        Employee employee= new Employee(name,pricePerHour,salmonCookiesStore);
        employeeRepository.save(employee);
        return new RedirectView("/");
    }
}