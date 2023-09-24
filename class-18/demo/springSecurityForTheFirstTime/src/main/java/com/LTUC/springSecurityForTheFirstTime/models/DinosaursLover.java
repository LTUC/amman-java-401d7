package com.LTUC.springSecurityForTheFirstTime.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
public class DinosaursLover implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private LocalDate localDate;

    // ======== class 18=========
    // Don't cascade here! You don't need it; the join table will update when users are removed
    @ManyToMany(mappedBy = "employees")
    Set<DinosaursLover> managers;

    // Don't cascade here! You don't need it; the join table will update when users are removed
    @ManyToMany
    @JoinTable(
            name = "dinomanagers_to_dinoemployees",  // table name
            joinColumns = {@JoinColumn(name="manager")},
            inverseJoinColumns = {@JoinColumn(name="employee")}
    )
    Set<DinosaursLover> employees;

    public DinosaursLover(){

    }

    public DinosaursLover(String username, String password, LocalDate localDate) {
        this.username = username;
        this.password = password;
        this.localDate = localDate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<DinosaursLover> getManagers() {
        return managers;
    }

    public void setManagers(Set<DinosaursLover> managers) {
        this.managers = managers;
    }

    public Set<DinosaursLover> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<DinosaursLover> employees) {
        this.employees = employees;
    }
}
