package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final UserService userService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PreAuthorize("hasAuthority('MEMBER')")
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
        return "project-view";
    }

    @GetMapping("/user-list")
    public String userListView(){
        return "user-list";
    }

    @GetMapping("/userProfile")
    public String userProfileView(){
        return "user-profile";
    }
  
    @GetMapping("/department")
    public String departmentView(){
        return "department";
    }

    @GetMapping("/gantt")
    public String ganttView(){
        return "gantt-chart";
    }

    @GetMapping("/project-details-view")
    public String details(){
        return "project-details-info";
    }



    @GetMapping("/all-issues")
    public String allIssue(){
        return "issue-all-display";
    }

    @GetMapping("/issue-create")
    public String issueCreate(){
        return "issue-create";
    }

}

