package com.LTUC.springSecurityIndustryWorld.bo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class CustomUserDetails implements UserDetails {

    private Long id;

    private String fullName;

    private String userName;

    private String email;

    private String phoneNumber;

    private String password;

    private String role;

    private String address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {

        return userName;
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


    public Map<String, Object> getClaims() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", this.id);
        claims.put("fullName", this.fullName);
        claims.put("userName", this.userName);
        claims.put("email", this.email);
        claims.put("phoneNumber", this.phoneNumber);
        claims.put("role", role);
        claims.put("address", this.address);
        return claims;
    }
}
