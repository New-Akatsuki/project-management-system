package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // Assuming you have configured email properties in your application.properties
    private String fromEmail;

    @Async
    @Override
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
