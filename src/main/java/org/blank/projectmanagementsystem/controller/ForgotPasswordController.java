package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.repository.ForgotPasswordRepository;
import org.blank.projectmanagementsystem.service.ForgotPasswordService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
