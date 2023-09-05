package com.furryfriendsfinder.salmonCookiesD7.models;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity

public class SalmonCookiesStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nameOfCookie", nullable = false)
    private String name;

    private LocalDateTime dateTime;


    public SalmonCookiesStore(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public SalmonCookiesStore() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
