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
import org.blank.projectmanagementsystem.domain.viewobject.ProjectEditViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.dto.AmountReportDto;
import org.blank.projectmanagementsystem.dto.ProjectReportDto;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        return ResponseEntity.ok(projectService.saveProject(projectFormInput));
    }

    @GetMapping("/get-projectDetails-byId/{id}")
    public ResponseEntity<ProjectEditViewObject> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectByID(id));
    }

    @PutMapping("/toggle-project-status/{id}")
    public ResponseEntity<String> toggleStatus(@PathVariable Long id){
        return ResponseEntity.ok(projectService.toggleProjectStatus(id));
    }

    @GetMapping("/export-project-pdf")
    public ResponseEntity<byte[]> export(@RequestParam Long projectId) {
        try {
            ProjectReportDto projectReportDto = new ProjectReportDto(projectService.getProject(projectId));
            Map<String, Object> dataBeans = new HashMap<>();
            dataBeans.put("project", projectReportDto);

            byte[] pdfBytes = reportService.generatePdf(dataBeans, "ProjectReport");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", (projectReportDto.getProjectName()+" report.pdf").toLowerCase());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export-project-xlsx")
    public ResponseEntity<byte[]> exportXlsx(@RequestParam Long projectId) {
        try {
            ProjectReportDto projectReportDto = new ProjectReportDto(projectService.getProject(projectId));
            Map<String, Object> dataBeans = new HashMap<>();
            dataBeans.put("project", projectReportDto);

            byte[] excelBytes = reportService.generateExcel(dataBeans, "ProjectReport"); // Use generateExcel instead of generatePdf

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Set content type to APPLICATION_OCTET_STREAM for binary data
            headers.setContentDispositionFormData("attachment", (projectReportDto.getProjectName()+" report.xlsx").toLowerCase());

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
