package org.blank.projectmanagementsystem.controller;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.domain.formInput.DefaultPasswordFormInput;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.EditUserFormInput;
import org.blank.projectmanagementsystem.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pm")
public class PMController {
    private final UserService userService;
    private final SystemOutlineService systemOutlineService;
    private final ClientService clientService;
    private final ArchitectureService architectureService;
    private final DeliverableService deliverableService;

    @GetMapping("/project-list")
    public String projectList() {
        return "project-list";
    }

//    @GetMapping("/user-list")
//    public String userList() {
//        return "user-list";
//    }
//    @GetMapping("/user-list-pm")
//    public String userListPM() {
//        return "user-list-pm";
//    }

//    @GetMapping("/user-profile")
//    public ModelAndView userprofile() {
//        return new ModelAndView("user-profile", "addUserFormInput", new AddUserFormInput());
//    }

    @PostMapping("/user-profile")
    public String userprofile(@ModelAttribute AddUserFormInput addUserFormInput) {
        return "user-list";
    }

    @GetMapping("/add-user")
    public ModelAndView addUser() {
        return new ModelAndView("add-user", "addUserFormInput", new AddUserFormInput());
    }

    @GetMapping("/project-detail")
    public String ProjectDetail() {
        return "project-detail";
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

