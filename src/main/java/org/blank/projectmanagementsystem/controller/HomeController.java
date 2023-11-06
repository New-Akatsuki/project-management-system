package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.TestEntityClass;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    private final DepartmentService departmentService;
    private final ClientService clientService;
    @GetMapping("/")
    public String index(ModelMap model){
        long userCouNT = userService.getAllUser().size();
        long clientCount = clientService.getallClients().size();
        long departmentCount = departmentService.getAllDepartments().size();
        List<Department> departments = departmentService.getAllDepartments();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            // Get the username from the authentication object
            String username = authentication.getName();
            model.addAttribute("username", username);

        }

        List<String> departmentNames = new ArrayList<>();
        List<Long> userCounts = new ArrayList<>();

        for (Department department : departments) {
            departmentNames.add(department.getName());
            long userCount = userService.getUserCountByDepartment(department);
            userCounts.add(userCount);
        }
        log.info("====================================================================================");
        log.info("Department Names: {}", departmentNames);
        log.info("User Counts: {}", userCounts);
        log.info("====================================================================================");



        model.addAttribute("departmentNames", departmentNames);
        model.addAttribute("userCounts", userCounts);
        model.addAttribute("userCount", userCouNT);
        model.addAttribute("clientCount", clientCount);
        model.addAttribute("departmenetCount", departmentCount);
//        model.addAttribute("departments", departments);
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
