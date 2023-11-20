package org.blank.projectmanagementsystem.controller.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/projects")
    public String showProjectLists() {
        var user = userService.getCurrentUser();
        if(user.getRole()== Role.MEMBER) {
            return "user-task-view";
        }
        return "project-list";
    }

    @GetMapping("/projects/new")
    public String createProject(){
        return "project-create";
    }

    @GetMapping("/projects/{id}/working-area")
    public String workingAreaByProjectId(@PathVariable Long id, Model model) {
        model.addAttribute("projectId", id);
        return "project-view";
    }

    @GetMapping("/projects/{id}/details")
    public ModelAndView showProjectDetails(@PathVariable Long id){
        ProjectViewObject project = projectService.getProjectById(id);
        return new ModelAndView("project-details-info","currentProject",project);
    }

}