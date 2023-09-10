package com.LTUCD7.salmonCookies2.models;

import javax.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name",nullable = false)
    private String name;

    @Column(name= "pay_per_hour", nullable = false)
    private Long payPerHour;

    @ManyToOne
    private SalmonCookiesStore employedAt;

    public Employee() {
    }

    public Employee(String name, Long payPerHour,SalmonCookiesStore employedAt) {
        this.name = name;
        this.payPerHour = payPerHour;
        this.employedAt=employedAt;
    }

    public SalmonCookiesStore getEmployedAt() {
        return employedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPayPerHour() {
        return payPerHour;
    }

    public void setPayPerHour(Long payPerHour) {
        this.payPerHour = payPerHour;
    }
}
