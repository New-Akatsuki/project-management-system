package org.blank.projectmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // Assuming you have configured email properties in your application.properties
    private String fromEmail;

    public void sendDefaultPassword(User user, String defaultPassword) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Dir-Ace Technology");

            helper.setText( "Dear " + user.getName() + ",\n\n" +
                    "Your account has been created successfully.\n\n" +
                    "Here is your default password: "  + defaultPassword+ "\n\n" +
                    "Please change your password as soon as you log in for the first time.\n\n" +
                    "Best Regards,\n\n" +
                    "The HR Team", true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
