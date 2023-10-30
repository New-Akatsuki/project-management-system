package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.TestEntityClass;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class HomeController {

    private final UserService userService;
    @GetMapping("/")
    public String index(){
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
    @GetMapping("/create-issue")
    public String issuessdfView(){
        return "create-issue";
    }

    @GetMapping("/issue-display")
    public String issuessdsdffView(){
        return "issue-display";
    }
}
