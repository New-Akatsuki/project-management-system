package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String changeDefaultPassword(@ModelAttribute DefaultPasswordFormInput defaultPasswordFormInput) {
        log.info("========================================================");
        log.info("default password: {}", defaultPasswordFormInput);
        log.info("========================================================\n");

        return "redirect:/";
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

    @PostMapping("/task-name")
    public String taskname(@ModelAttribute TaskFormInput taskFormInput){
        log.info("================================================");
        log.info("task name: {}", taskFormInput);
        log.info("================================================\n");

        SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return "redirect:/";
    }

    @GetMapping("/create-issue")
    public ModelAndView createIssue(){
        return new ModelAndView("issue-create", "issueCreateFormInput", new IssueCreateFormInput());
    }

    @PostMapping("/create-issue")
    public String createIssue(@ModelAttribute IssueCreateFormInput issueCreateFormInput){
        log.info("========================================================");
        log.info("issue: {}", issueCreateFormInput);
        log.info("========================================================\n");
        return "redirect:/";
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
