package com.spring.controller;


import com.spring.entities.*;
import com.spring.repository.ClaimsRepository;
import com.spring.repository.ProjectDetailRepository;
import com.spring.repository.ProjectRepository;
import com.spring.repository.StaffRepository;
import com.spring.sevices.AuthServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CreateClaimsController {
    @Autowired
    private ProjectRepository projectReponsitory;

    @Autowired
    AuthServices authServices;

    @Autowired
    private StaffRepository staffReponsitory;

    @Autowired
    private ProjectDetailRepository projectDetailReponsitory;
    @Autowired
    private ClaimsRepository claimsRepository;

    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/claims/create")
    public String claimsPage( Model model, HttpSession session
                             ) {
        Staff staff = authServices.getCurrentUser().getStaffDb();
        if(staff!=null){
           Claims claim = new Claims();
            if (claim.getClaimDays() == null || claim.getClaimDays().isEmpty()) {
                claim.setClaimDays(new ArrayList<>());
                claim.getClaimDays().add(new ClaimsDetails()); // Thêm 1 hàng trống ban đầu
            }
            claim.setStaff(staff);
            model.addAttribute("claims", claim);

            List<ProjectDetail> projectDetails = projectDetailReponsitory.findByProjectDetailKeyStaffId(staff.getStaffId());
            List<Integer> ids = new ArrayList<>();

            for (ProjectDetail projectDetail : projectDetails) {
                ids.add(projectDetail.getProject().getId());
            }
            List<Project> projects = projectReponsitory.findAllById(ids);
            Map<Integer, String> projectRoles = new HashMap<>();
            for (Project project : projects) {
                String roles = projectDetails.stream()
                        .filter(pd -> pd.getProject().getId().equals(project.getId()))
                        .map(ProjectDetail::getRoleProject)
                        .collect(Collectors.joining(", "));
                projectRoles.put(project.getId(), roles);
            }
            model.addAttribute("projects",projects);
            model.addAttribute("projectRoles", projectRoles);

        }

        return "claims/createClaims";
    }
    @PostMapping("/claims/save")
    public String saveClaims(@RequestParam("projectName") String id,
                             @RequestParam("action") String action,
                             @ModelAttribute("claims") Claims claims,
                             Model model,
                             HttpServletRequest request,
                             HttpSession session){
        Integer projectid = Integer.parseInt(id);
        claims.setProject(projectReponsitory.findById(projectid).get());
        List<ClaimsDetails> days = new ArrayList<>();
        int dayIndex = 0;
        while (request.getParameter("claimDays[" + dayIndex + "].date") != null) {
            ClaimsDetails day = new ClaimsDetails();
            day.setDate(LocalDate.parse(request.getParameter("claimDays[" + dayIndex + "].date")));
            day.setDay(request.getParameter("claimDays[" + dayIndex + "].day"));
            day.setFromDate(LocalTime.parse(request.getParameter("claimDays[" + dayIndex + "].fromDate")));
            day.setToDate(LocalTime.parse(request.getParameter("claimDays[" + dayIndex + "].toDate")));
            day.setTotalOfHours(Double.parseDouble(request.getParameter("claimDays[" + dayIndex + "].totalOfHours")));
            day.setDescription(request.getParameter("claimDays[" + dayIndex + "].description"));
            days.add(day);
            dayIndex++;
        }

        if ("save".equals(action)) {
            claims.setStatus(Status.Draft);
        } else if ("submit".equals(action)) {
            claims.setStatus(Status.Pending_Approval);
        }
        claims.setClaimDays(days);
        claimsRepository.save(claims);
        return "redirect:/claims/view";

    }

}
