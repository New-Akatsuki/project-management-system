package org.blank.projectmanagementsystem.service;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.ForgotPasswordToken;
import org.blank.projectmanagementsystem.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final ForgotPasswordRepository fpRepo;
    private final JavaMailSender javaMailSender;
    private final int DAYS = 3;

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime expireTimeRange () {
        return LocalDateTime.now().plusHours(DAYS);
    }

    public void sendEmail(String to,String subject, String emailLink) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper (message);
        String emailContent = "<p>Hello</p>"
                +"Click the link to reset Password"
                +"<p><a href=\" " + emailLink + " \">Change My Password</a> </p>"+
                "<br>";
        helper.setText(emailContent, true);
        helper.setFrom("minmratbhoneaung.etc@gmail.com", "Dir-ACE Technology");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);

    }

    public boolean isExpired  (ForgotPasswordToken fwToken) {
        return LocalDateTime.now().isAfter(fwToken.getExpireTime());

    }

    public String checkValidity(ForgotPasswordToken fwToken,Model model) {
        if (fwToken == null) {
            model.addAttribute("error", "Invalid Token");
            return "error";
        }
        else if (fwToken.isUsed()) {
            model.addAttribute("error", "The Token is already Used");
            return "used-token";
        }
        else if (isExpired(fwToken)) {
            model.addAttribute("error", "The Token is Expired");
            return "expired-token";

        }else {
            return "reset-password";
        }

    }

}