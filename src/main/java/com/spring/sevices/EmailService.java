package com.spring.sevices;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.thymeleaf.context.Context;

public interface EmailService {

    void sendEmail(String to, String subject, String content) throws MessagingException;
}
