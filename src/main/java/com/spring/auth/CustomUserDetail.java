package com.spring.auth;

import com.spring.entities.Staff;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@ToString
public class CustomUserDetail implements UserDetails {


    final Staff staffDb;

    public CustomUserDetail(Staff staffDb) {
        this.staffDb = staffDb;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = this.staffDb.getRoleStaff().name();
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return staffDb.getPassword();
    }

    @Override
    public String getUsername() {
        return staffDb.getEmail();
    }
}
