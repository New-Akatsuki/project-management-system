package org.blank.projectmanagementsystem.controller.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.service.AmountService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReviewCountService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;
    private final AmountService amountService;
    private final ReviewCountService reviewCountService;

    @GetMapping("/projects")
    public String showProjectLists(@RequestParam(required = false)Long projectId,Model model) {
        var user = userService.getCurrentUser();
        model.addAttribute("pId",projectId);
        if(user.getRole()== Role.MEMBER) {
            return "user-task-view";
        }
        return "project-list";
    }

    @PreAuthorize("hasAuthority('PM')")
    @GetMapping("/projects/new")
    public String createProject(){
        return "project-create";
    }

    @PreAuthorize("hasAuthority('PM')")
    @GetMapping("/projects/{id}/{name}/workspace")
    public String workingAreaByProjectId(@PathVariable Long id, Model model, @PathVariable String name) {
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", name);
        return "project-view";
    }

    @PreAuthorize("hasAnyAuthority('PMO','SDQC','Dh','PM')")
    @GetMapping("/projects/{id}/{name}/details")
    public ModelAndView showProjectDetails(@PathVariable Long id, @PathVariable String name){
        ProjectViewObject project = projectService.getProjectById(id);
        return new ModelAndView("project-details-info","currentProject",project);
    }

    @PreAuthorize("hasAnyAuthority('PM')")
    @GetMapping("/projects/{id}/{name}/edit")
    public String projectEditView(@PathVariable Long id, @PathVariable String name, Model model){
        model.addAttribute("projectId", id);
        return "project-edit";
    }
}
