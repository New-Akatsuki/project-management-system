package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ProjectService projectService;

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
        return "issue-display";
    }


    @GetMapping("/project-details-view")
    public String details(){
        return "project-details-info";
    }

    @GetMapping("/change-password")
    public String detailssd(){
        return "change-password";
    }

    @PreAuthorize("hasAuthority('PM')")
    @GetMapping("/notification")
    public String notification(){
        return "notification";
    }

    // project details view //==DON'T DELETE==
    @GetMapping("/projects/{id}/details")
    public ModelAndView showProjectDetial(@PathVariable Long id){
        ProjectViewObject project = projectService.getProjectById(id);
        return new ModelAndView("project-details-info","currentProject",project);
    }

}

