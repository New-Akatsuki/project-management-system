package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.TestEntityClass;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.service.ClientService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class HomeController {

    private final UserService userService;
    private final DepartmentRepository departmentRepository;
    private final ClientService clientService;
    @GetMapping("/")
    public String index(ModelMap model){
        long userCount = userService.getAllUser().size();
        long clientCount = clientService.getallClients().size();
        long departmentCount = departmentRepository.findAll().size();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            // Get the username from the authentication object
            String username = authentication.getName();
            model.addAttribute("username", username);

        }
        model.addAttribute("userCount", userCount);
        model.addAttribute("clientCount", clientCount);
        model.addAttribute("departmenetCount", departmentCount);
        return "index";
    }

    @GetMapping("/task-view")
    public String task(){
        return "task-view";
    }

    @GetMapping("/project-view")
    public String project() {
        return "project-view";
    }
    @GetMapping("/kan-ban")
    public String kanBanView(ModelMap model) {
        return "test";
    }

    @GetMapping("/user-list")
    public String userListView(){
        return "user-list";
    }

    @GetMapping("/userProfile")
    public String userProfileView(){
        return "user-profile";
    }
  
    @GetMapping("/issues")
    public String issuesView(){
        return "issue-page";
    }
  
    @GetMapping("/department")
    public String departmentView(){
        return "department";
    }
    @GetMapping("/create-issue-page")
    public String issuessdfView(){
        return "create-issue-page";
    }

    @GetMapping("/issue-display-page")
    public String issuessdsdffView(){
        return "issue-display-page";
    }
}
