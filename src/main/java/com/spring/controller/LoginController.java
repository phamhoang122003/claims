package com.spring.controller;

import com.spring.auth.CustomUserDetail;
import com.spring.entities.ProjectDetail;
import com.spring.entities.RoleStaff;
import com.spring.entities.Staff;
import com.spring.repository.ProjectDetailRepository;
import com.spring.repository.StaffRepository;
import com.spring.sevices.AuthServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private AuthServices authServices;


    @GetMapping("/login")
    public String loginForm(Model model) {
        Object userLogger =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!(userLogger  instanceof UserDetails)) {
            model.addAttribute("staff", new Staff());
            return "login";
        }
        model.addAttribute("staffName",((UserDetails) userLogger).getUsername());
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping(value = {"/","/home"})
    public String home(Model model) {
        Staff staff = authServices.getCurrentUser().getStaffDb();
        if(staff.getRoleStaff().equals(RoleStaff.ADMIN)){
            return "redirect:/project/list";
        }
        return "redirect:/claims/view";
    }




}
