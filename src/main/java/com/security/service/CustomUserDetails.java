package com.security.service;

import java.util.ArrayList;
import java.util.Collection;
import com.security.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CustomUserDetails implements UserDetails {

    private final transient UserEntity userEntity;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((GrantedAuthority) () -> userEntity.getRole().name());

        return collection;
    }


    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }


    @Override
    public String getUsername() {
        return userEntity.getUsername();
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
}
