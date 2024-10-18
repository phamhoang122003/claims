package com.spring.auth;


import com.spring.entities.Staff;
import com.spring.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    StaffRepository staffRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Staff> staff = staffRepository.findStaffByEmail(username);
        if(staff.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        Staff staffdb = staff.get();


        return new CustomUserDetail(staffdb);
    }
}
