package org.blank.projectmanagementsystem.controller.api;

import com.rabbitmq.client.impl.recovery.RecordedEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectAPI {

    private final ProjectService projectService;

    @GetMapping("/get-projects")
    public List<ProjectListViewObject> getProject() {
        List<ProjectListViewObject> proj= projectService.getAllProjects();
        log.info("get proj{} \n\n", proj);
        return proj;
    }
    @PostMapping("/add-project")
    public ResponseEntity<Project> createProject(@RequestBody ProjectFormInput projectFormInput) {
        log.info("create project {} \n\n", projectFormInput);
        return ResponseEntity.ok(projectService.saveProject(projectFormInput));
    }

    @GetMapping("/get-projectDetails-byId/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        log.info("get project by id {} \n\n", id);
        Project project = projectService.getProjectByID(id);
        return ResponseEntity.ok(project);
    }

}
