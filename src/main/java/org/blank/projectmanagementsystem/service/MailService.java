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
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}") // Assuming you have configured email properties in your application.properties
    private String fromEmail;

    public void sendDefaultPassword(User user, String defaultPassword) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Your Application");

            // You can use a Thymeleaf template to customize your email content
            Context context = new Context();
            context.setVariable("user", user);
            context.setVariable("defaultPassword", defaultPassword);

            String emailContent = templateEngine.process("default-password-email-template", context); // Create a Thymeleaf template for your email content

            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
