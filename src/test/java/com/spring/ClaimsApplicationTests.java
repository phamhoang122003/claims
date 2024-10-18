package com.spring;

import com.spring.dto.StaffDTO;
import com.spring.entities.Claims;
import com.spring.entities.ProjectDetail;
import com.spring.entities.Staff;
import com.spring.repository.*;
import com.spring.sevices.EmailService;
import com.spring.sevices.ProjectDetailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.context.Context;

import java.util.List;

@SpringBootTest
class ClaimsApplicationTests {

	private final ProjectDetailService projectDetailService;
	private final ProjectDetailCustom custom;
	private final StaffRepository staffRepository;
	private final ClaimsRepository claimsRepository;
	private final EmailService emailService;

	@Autowired
	ProjectDetailRepository projectDetailRepository;
	@Autowired
    ClaimsApplicationTests(ProjectDetailService projectDetailService, ProjectDetailCustom custom, StaffRepository staffRepository, ClaimsRepository claimsRepository, EmailService emailService) {
        this.projectDetailService = projectDetailService;
        this.custom = custom;
        this.staffRepository = staffRepository;
        this.claimsRepository = claimsRepository;
        this.emailService = emailService;
    }

    @Test
	void contextLoads() {
		List<Claims> pendingClaims = claimsRepository.findPendingApprovalClaims();
		System.out.println(pendingClaims.size());
		for (Claims projectDetail1 : pendingClaims){
			System.out.println(projectDetail1);
		}
	}

	@Test
	void contextLoads1() {
		Staff staff = staffRepository.findByEmail("phamhoaang10a2nt@gmail.com");
		List<ProjectDetail> projectDetails = projectDetailRepository.findByProjectDetailKeyStaffId(staff.getStaffId());
		for (ProjectDetail projectDetail1 : projectDetails)
		{
			System.out.println(projectDetail1.getProjectDetailKey().getProjectId());
		}
	}


}
