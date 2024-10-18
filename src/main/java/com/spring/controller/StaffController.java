package com.spring.controller;

import com.spring.annotationEmail.UniqueEmailValidator;
import com.spring.entities.RoleStaff;
import com.spring.entities.Staff;
import com.spring.repository.StaffRepository;
import com.spring.validation.CreateGroup;
import com.spring.validation.UpdateGroup;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;



@Controller
public class StaffController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StaffRepository staffRepository;

    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
    @RequestMapping(value="/staff/list",method = {RequestMethod.GET, RequestMethod.POST})
    public String staffListPage(
            Model model,
            @RequestParam(value = "page" ,defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "search" ,required = false) String search
    ) {
        int pageSizeDefault = 5;
        Pageable pageable = PageRequest.of(pageNumber-1, pageSizeDefault);
        Page<Staff> page = null;

        if(search == null || search.isBlank()){
            page = staffRepository.findAll(pageable);

        }else {
            page = staffRepository.findByStaffNameOrDepartmentOrJobRankOrEmail("%"+search+"%" ,pageable);
        }

        //tinh list page
        int totalPages = page.getTotalPages();


        List<Integer> pageNumber1 = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumber1.add(i);
        }
        model.addAttribute ("pageNumber", pageNumber1);
        model.addAttribute("page", page);
        return "staff/staffList";
    }
    @GetMapping("/staff/create")
    public String createStaff(Model model){
        model.addAttribute("staff", new Staff());
        return "staff/staffCreate";
    }
    @PostMapping("/staff/create")
    public String saveStaff(
            @Validated(value = {CreateGroup.class, UpdateGroup.class}) @ModelAttribute("staff") Staff staff,
            BindingResult bindingResult,
            RedirectAttributes attributes) {

        try {
            UniqueEmailValidator.setCurrentStaff(staff);

            // Kiểm tra bindingResult để xem có lỗi không
            if (bindingResult.hasErrors()) {
                return "staff/staffCreate"; // Trả lại trang với lỗi
            }

            // Nếu không có lỗi, tiến hành mã hóa mật khẩu
            String rawPassword = staff.getPassword();
            String encryptPassword = passwordEncoder.encode(rawPassword);
            staff.setPassword(encryptPassword);
            staff.setRoleStaff(RoleStaff.USER);

            // Kiểm tra lại một lần nữa trước khi lưu
            if (staffRepository.existsByEmail(staff.getEmail())) {
                bindingResult.rejectValue("email", "error.staff", "Email already exists");
                return "staff/staffCreate"; // Trả lại trang với lỗi
            }

            staffRepository.save(staff);
            attributes.addFlashAttribute("message", "Changes about staff have been updated");
            return "redirect:/staff/list"; // Chuyển hướng khi thành công
        } finally {
            UniqueEmailValidator.clear();
        }
    }
    @GetMapping("/staff/edit")
    public String editStaff(
            @RequestParam("id") Integer id,
            Model model){
        Staff staff = staffRepository.findById(id).orElse(null);
        if(staff == null){
            return "redirect:/staff/list";
        }
        model.addAttribute("staff", staff);
        return "staff/staffCreate";
    }

    @GetMapping("/staff/delete")
    public String deleteStaff(
            @RequestParam("id") Integer id,
            RedirectAttributes attributes){
        Staff staff = staffRepository.findById(id).orElse(null);
        if(staff!= null){
            staffRepository.delete(staff);
            attributes.addFlashAttribute("message", "Staff deleted successfully!");
        }
        return "redirect:/staff/list";
    }
}
