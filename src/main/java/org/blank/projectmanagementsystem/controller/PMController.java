package org.blank.projectmanagementsystem.controller;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.EditUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pm")
@Slf4j
public class PMController {
    private final UserService userService;

    @GetMapping("/project-list")
    public String projectList() {
        return "project-list";
    }


    @GetMapping("/user-list")
    public String userList() {
        return "user-list";
    }

    @GetMapping("/user-profile")
    public ModelAndView userprofile() {
        return new ModelAndView("userprofile", "addUserFormInput", new AddUserFormInput());
    }

    @PostMapping("/user-profile")
    public String userprofile(@ModelAttribute AddUserFormInput addUserFormInput) {
        return "user-list";
    }

    @GetMapping("/add-user")
    public ModelAndView addUser() {
        return new ModelAndView("add-user", "addUserFormInput", new AddUserFormInput());
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute AddUserFormInput addUserFormInput) {
        log.info("================================================");
        log.info("adduser: {}", addUserFormInput);
        log.info("================================================\n");
        userService.registerUser(addUserFormInput);
        return "redirect:/";
    }

    @GetMapping("/edit-user")
    public ModelAndView editUser() {
        return new ModelAndView("edit-user", "editUserFormInput", new EditUserFormInput());

    }

    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute EditUserFormInput editUserFormInput) {
        return "/user-list";
    }

    @GetMapping("/project-details-info")
    public String ProjectDetail() {
        return "project-details-info";
    }

    @GetMapping("/create-project")
    public String CreateProject() {
        return "create-new-project";
    }

    @GetMapping("/edit-project")
    public String EditProject() {
        return "edit-project";
    }


    @GetMapping("/contract-info")
    public String ContractInfo() {
        return "contract-info";
    }

    @GetMapping("/department")
    public String Department() {
        return "department";
    }
}

