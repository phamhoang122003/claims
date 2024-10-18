package com.spring.sevices;

import com.spring.auth.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    public CustomUserDetail getCurrentUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object userDetail =  authentication.getPrincipal();
        if (userDetail instanceof UserDetails) {
            return (CustomUserDetail) userDetail;
        }
        return null;
    }
}
