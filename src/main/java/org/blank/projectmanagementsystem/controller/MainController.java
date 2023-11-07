package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.service.ClientService;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final UserService userService;
    private final ClientService clientService;
    private final DepartmentService departmentService;
    private final ProjectService projectService;

    @GetMapping("/")
    public String index(ModelMap model){
        long userCount = userService.getAllUsers().size();
        long clientCount = clientService.getAllClients().size();
        long departmentCount = departmentService.getAllDepartments().size();

        List<ProjectViewObject> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("userCount", userCount);
        model.addAttribute("clientCount", clientCount);
        model.addAttribute("departmentCount", departmentCount);

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


    @GetMapping("/gantt")
    public String ganttView(){
        return "gantt-chart";
    }

    @GetMapping("/project-details-view")
    public String details(){
        return "project-details-info";
    }

    @GetMapping("/change-password")
    public String detailssd(){
        return "change-password";
    }

    @GetMapping("/man-month")
    public String manMonthView(){
        return "man-month";
    }

}

