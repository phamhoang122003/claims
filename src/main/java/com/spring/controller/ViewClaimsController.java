package com.spring.controller;

import com.spring.entities.Claims;
import com.spring.entities.Staff;
import com.spring.entities.Status;
import com.spring.repository.*;
import com.spring.sevices.AuthServices;
import com.spring.sevices.ClaimsService;
import com.spring.sevices.ClaimsServiceImple;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ViewClaimsController {

    @Autowired
    AuthServices authServices;

    @Autowired
    private ClaimsRepository claimsRepository;

    @Autowired
    private ClaimsService claimsService = new ClaimsServiceImple();


    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
    @GetMapping("/claims/view")
    public String viewClaims(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size,
                             @RequestParam(defaultValue = "All") String status,
                             @RequestParam(defaultValue = "id") String sort,
                             @RequestParam(defaultValue = "asc") String direction,
                             Model model) {
        Staff staff = authServices.getCurrentUser().getStaffDb();
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size,Sort.by(sortDirection,sort));

        Page<Claims> claimsPage;

        if ("All".equals(status)) {
            claimsPage = claimsRepository.findAllByStaffStaffId(staff.getStaffId(),pageable);
        } else {
            claimsPage = claimsRepository.findAllByStaffStaffIdAndStatus(staff.getStaffId(), Status.valueOf(status), pageable);
        }

        model.addAttribute("claimsPage", claimsPage);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", claimsPage.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);

        return "claims/ViewClaims";
    }
    
}
