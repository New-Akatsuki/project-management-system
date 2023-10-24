package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.formInput.DefaultPasswordFormInput;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final UserService userService;

    @GetMapping("/change-default-password")
    public ModelAndView changeDefaultPassword() {
        return new ModelAndView("default-password", "defaultPasswordFormInput", new DefaultPasswordFormInput());
    }

    @PostMapping("/change-default-password")
    public String changeDefaultPassword(@ModelAttribute DefaultPasswordFormInput defaultPasswordFormInput) {
        log.info("========================================================");
        log.info("default password: {}", defaultPasswordFormInput);
        log.info("========================================================\n");
        return "redirect:/";
    }


}
