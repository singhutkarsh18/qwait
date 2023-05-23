package com.example.qwait.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter@Setter
@Document
public class AppUser implements UserDetails {

    @Id
    private Long id;
    private String name;
    private String username;
    private String password;
    private Long mob;
    private String gender;
    private Boolean locked=true;
    private Boolean enabled=false;
    private Role role;
    private int otp;

    private Store store;
    public AppUser(String name, String username, String password,int otp,Role role,String gender,Long mob) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.otp=otp;
        this.role=role;
        this.gender=gender;
        this.mob=mob;
    }

    public AppUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
