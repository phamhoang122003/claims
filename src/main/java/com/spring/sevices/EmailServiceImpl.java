package com.spring.sevices;

import com.spring.repository.ClaimsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;
    private final ClaimsRepository claimRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, ClaimsRepository claimRepository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.claimRepository = claimRepository;
    }

    @Override
    public void sendEmail(String to, String subject, String content) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        // Sử dụng MimeMessageHelper để cấu hình thông tin email
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // true để gửi email ở định dạng HTML

        // Gửi email
        mailSender.send(message);
    }
}
