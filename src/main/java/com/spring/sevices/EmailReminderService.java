package com.spring.sevices;

import com.spring.entities.Claims;
import com.spring.entities.ClaimsDetails;
import com.spring.entities.ProjectDetail;
import com.spring.repository.ClaimsRepository;
import com.spring.repository.ProjectDetailRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailReminderService {


    private final ClaimsRepository claimRepository;


    private final EmailService emailService;
    private final ProjectDetailRepository projectDetailRepository;


    private final TemplateEngine templateEngine;

    @Autowired
    public EmailReminderService(ClaimsRepository claimRepository, EmailService emailService, ProjectDetailRepository projectDetailRepository, TemplateEngine templateEngine) {
        this.claimRepository = claimRepository;
        this.emailService = emailService;
        this.projectDetailRepository = projectDetailRepository;
        this.templateEngine = templateEngine;
    }

//    @Scheduled(cron = "0 25 19 * * ?")
    @Scheduled(cron = "0 52 18 * * ?")
    public void sendDailyReminderEmails() throws MessagingException {

        List<Claims> pendingClaims = claimRepository.findPendingApprovalClaims();
        if(!pendingClaims.isEmpty()){
            Map<Integer, List<Claims>> listMap = pendingClaims.stream()
                    .collect(Collectors.groupingBy(claim -> claim.getProject().getId()));

            for (Map.Entry<Integer, List<Claims>> entry : listMap.entrySet()) {
                Integer projectId = entry.getKey();
                ProjectDetail projectDetail = projectDetailRepository.findByProjectIdAndRoleProject(projectId, "PM");
                List<Claims> claims = entry.getValue();
                String subject = "Daily Request Approval Reminder";
                String content = generateEmailContent(claims);
                emailService.sendEmail(projectDetail.getStaff().getEmail(), subject, content);
            }
        }
    }
    private String generateEmailContent(List<Claims> claims) {
        Context context = new Context();
        context.setVariable("claims", claims);
        context.setVariable("size", claims.size());
        return templateEngine.process("send-email", context);
    }


}
