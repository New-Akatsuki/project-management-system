package org.blank.projectmanagementsystem.controller.api;

import com.rabbitmq.client.impl.recovery.RecordedEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.dto.AmountReportDto;
import org.blank.projectmanagementsystem.dto.ProjectReportDto;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectAPI {

    private final ProjectService projectService;
    private final ReportService reportService;

    @GetMapping("/get-projects")
    public List<ProjectListViewObject> getProject() {
        List<ProjectListViewObject> proj = projectService.getAllProjects();
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

    @GetMapping("/export-project-pdf")
    public void export(HttpServletResponse response) {
        ProjectReportDto projectReportDto = new ProjectReportDto(projectService.getProject(1L));

        Map<String, Object> dataBeans = new HashMap<>();
        dataBeans.put("project", projectReportDto);
//        dataBeans.put("contractMember", projectReportDto.getContractMembers());


        reportService.generatePdf(response, dataBeans, "ProjectReport", "project-report");
    }

}
