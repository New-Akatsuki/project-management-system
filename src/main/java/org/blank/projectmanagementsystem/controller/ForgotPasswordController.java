package org.blank.projectmanagementsystem.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.ForgotPasswordToken;
import org.blank.projectmanagementsystem.domain.entity.Login;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.ForgotPasswordRepository;
import org.blank.projectmanagementsystem.service.ForgotPasswordService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {
    private final UserService userService;
    private final ForgotPasswordRepository fwRepo;
    private final ForgotPasswordService fwService;

    @GetMapping("/forgot-password")
    public String PasswordForget(){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String savePasswordRequest(@RequestParam("email") String  email, Model model, Login login) {
        User user=userService.getEmail(login);
        if(user== null) {
            model.addAttribute("error", "This Email is not Registered");
            return "password-request";
        }

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setExpireTime(fwService.expireTimeRange());
        forgotPasswordToken.setToken(fwService.generateToken());
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setUsed(false);
        fwRepo.save(forgotPasswordToken);
        String emailLink ="http://localhost:8080/resetPassword?token=" + forgotPasswordToken.getToken();
        try {
            fwService.sendEmail(user.getEmail(), "Password Reset Link", emailLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error sending Email");
            return "password-request";
        }
        return "redirect:/password-request?success";
    }

}
