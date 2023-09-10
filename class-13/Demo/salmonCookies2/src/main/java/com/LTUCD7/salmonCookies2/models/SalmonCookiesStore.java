package com.LTUCD7.salmonCookies2.models;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity

public class SalmonCookiesStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nameOfCookie", nullable = false)
    private String name;

    private Long averageCookiesPerDay;

    @OneToMany(mappedBy = "employedAt", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public SalmonCookiesStore(String name, Long averageCookiesPerDay) {
        this.name = name;
        this.averageCookiesPerDay = averageCookiesPerDay;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalmonCookiesStore() {

    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Long getAverageCookiesPerDay() {
        return averageCookiesPerDay;
    }

    public void setAverageCookiesPerDay(Long averageCookiesPerDay) {
        this.averageCookiesPerDay = averageCookiesPerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
