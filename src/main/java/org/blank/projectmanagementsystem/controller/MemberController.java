package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;

import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;

import org.blank.projectmanagementsystem.domain.formInput.DefaultPasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueCreateFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueSolveFormInput;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class MemberController {
    private final UserService userService;

    @GetMapping("/change-default-password")
    public ModelAndView changeDefaultPassword() {
        return new ModelAndView("default-password", "defaultPasswordFormInput", new DefaultPasswordFormInput());
    }

    @PostMapping("/change-default-password")
    public ModelAndView changeDefaultPassword(@ModelAttribute DefaultPasswordFormInput defaultPasswordFormInput, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("default-password", "defaultPasswordFormInput", defaultPasswordFormInput);
        }
        log.info("defaultPasswordFormInput {}\n\n\n\n",defaultPasswordFormInput);
        userService.updatePassword(defaultPasswordFormInput.getPassword());
        return new ModelAndView("redirect:/");
    }

    // You would implement a method to retrieve the currently logged in user
    // This may vary depending on your authentication setup
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/change-password")
    public ModelAndView changePassword() {
        return new ModelAndView("change-password", "changePasswordFormInput", new ChangePasswordFormInput());
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute ChangePasswordFormInput changePasswordFormInput) {
        log.info("========================================================");
        log.info("change password: {}", changePasswordFormInput);
        log.info("========================================================\n");

        userService.changePassword(changePasswordFormInput.getCurrentPassword(), changePasswordFormInput.getNewPassword());

        return "redirect:/";
    }

    @GetMapping("/task-name")
    public String taskname() {
        return "task-name";
    }



    @GetMapping("/create-issue")
    public ModelAndView createIssue(){
        return new ModelAndView("issue-create", "issueCreateFormInput", new IssueCreateFormInput());
    }


    @GetMapping("/display-issue")
    public ModelAndView displayIssue(){
        return new ModelAndView("issue-display", "IssueSolveFormInput", new IssueSolveFormInput());
    }

    @GetMapping("/projects")
    public String projects(){
        return "project-view";
    }
}
