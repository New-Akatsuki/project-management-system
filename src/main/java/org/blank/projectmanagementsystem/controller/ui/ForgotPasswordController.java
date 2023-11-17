package org.blank.projectmanagementsystem.controller.ui;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.ForgotPasswordToken;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.ForgotPasswordRepository;
import org.blank.projectmanagementsystem.service.ForgotPasswordService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/forgot-password")
    public String PasswordForget(){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String savePasswordRequest(@RequestParam("email") String  email, Model model) {
        var userOptional=userService.getEmail(email);
        if(userOptional.isEmpty()) {
            model.addAttribute("error", "This Email is not Registered");
            return "forgot-password";
        }

        User user = userOptional.get();

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setExpireTime(fwService.expireTimeRange());
        forgotPasswordToken.setToken(fwService.generateToken());
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setUsed(false);
        fwRepo.save(forgotPasswordToken);
        String emailLink ="http://localhost:8080/reset-password?token=" + forgotPasswordToken.getToken();
        try {
            fwService.sendEmail(user.getEmail(), "Password Reset Link", emailLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error sending Email");
            return "forgot-password";
        }
        return "redirect:/forgot-password?success";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@Param(value= "token") String token, Model model, HttpSession session) {
        session.setAttribute( "token", token);
        ForgotPasswordToken fpToken=fwRepo.findByToken(token);
        return fwService.checkValidity(fpToken, model);

    }

    @PostMapping("/reset-password")
    public String savePassword(HttpServletRequest request, HttpSession session, Model model) {
        String password = request.getParameter("password");
        String token=(String) session.getAttribute("token");
        ForgotPasswordToken fwToken=fwRepo.findByToken(token);
        User user=fwToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        fwToken.setUsed(true);
        userService.save(user);
        fwRepo.save(fwToken);
        model.addAttribute("msg", "Password Reset is successful!");
        return "login";
    }

}