package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueSolveFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ResPartyFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;
import org.blank.projectmanagementsystem.dto.IssueReportDto;
import org.blank.projectmanagementsystem.dto.ProjectReportDto;
import org.blank.projectmanagementsystem.repository.ResponsiblePartyRepository;
import org.blank.projectmanagementsystem.service.IssueService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IssueAPI {
    private final IssueService issueService;
    private final ProjectService projectService;
    private final ReportService reportService;

    @GetMapping("/get-category")
    public ResponseEntity<List<IssueCategory>> getIssueCategory() {
        List<IssueCategory> issueCategories = issueService.getAllCategory();
        return ResponseEntity.ok(issueCategories);
    }
    @GetMapping("/get-place")
    public ResponseEntity<List<IssuePlace>> getIssuePlace() {
        List<IssuePlace> issuePlaces = issueService.getAllPlace();
        return ResponseEntity.ok(issuePlaces);
    }
    @GetMapping("/get-resParty")
    public ResponseEntity<List<ResponsibleParty>> getResponsibleParty() {
        List<ResponsibleParty> responsibleParty = issueService.getAllResponsibleParty();
        log.info("get responsible party {} \n\n", responsibleParty);
        return ResponseEntity.ok(responsibleParty);
    }
    @GetMapping("/get-pic")
    public List<User> getPic() {
        List<User> pic = projectService.getUsersByOngoingProject();
        log.info("get pic {} \n\n", pic);
        return pic;
    }
    @PostMapping("/create-issue")
    public ResponseEntity<Issue> createIssue(@RequestBody IssueFormInput issueFormInput) {
        log.info("create issue {} \n\n", issueFormInput);
        return ResponseEntity.ok(issueService.createIssue(issueFormInput));
    }
    @PostMapping("/create-resParty")
    public ResponseEntity<ResponsibleParty> createResParty(@RequestBody ResponsibleParty responsibleParty) {
        log.info("create resParty {} \n\n", responsibleParty);
        return ResponseEntity.ok(issueService.createResponsibleParty(responsibleParty));
    }
    @PostMapping("/create-place")
    public ResponseEntity<IssuePlace> createIssuePlace(@RequestBody IssuePlace issueplace) {
        log.info("create issue place");
        return ResponseEntity.ok(issueService.createIssuePlace(issueplace));
    }
    @PostMapping("/create-category")
    public ResponseEntity<IssueCategory> createIssueCategory(@RequestBody IssueCategory issueCategory) {
        log.info("create issue category");
        return ResponseEntity.ok(issueService.createIssueCategory(issueCategory));
    }
    @GetMapping("/get-all-issue")
    public ResponseEntity<List<AllIssueDisplayViewObject>> getAllIssue() {
        List<AllIssueDisplayViewObject> allIssuesViewObject = issueService.getAllIssueDisplayViewObject();
        log.info("get all issue {} \n\n", allIssuesViewObject);
        return ResponseEntity.ok(allIssuesViewObject);
    }

    @GetMapping("/get-issue-details/{id}")
    public ResponseEntity<IssueDetailsViewObject> getIssueDetailsById(@PathVariable Long id) {
        IssueDetailsViewObject issueDetailsViewObject = issueService.getIssueDetailsById(id);
        log.info("get issue details {} \n\n", issueDetailsViewObject);
        return ResponseEntity.ok(issueDetailsViewObject);
    }

    @GetMapping("/get-issue-solution/{id}")
    public ResponseEntity<IssueSolutionViewObject> getIssueSolutionById(@PathVariable Long id) {
        IssueSolutionViewObject issueSolutionViewObject = issueService.getIssueSolutionById(id);
        log.info("get issue solution {} \n\n", issueSolutionViewObject);
        return ResponseEntity.ok(issueSolutionViewObject);
    }

    @GetMapping("/get-issue-by-pic/{id}")
    public ResponseEntity<List<AllIssueDisplayViewObject>> getIssueByPic(@PathVariable Long id) {
        List<AllIssueDisplayViewObject> allIssuesViewObject = issueService.getAllIssueByPic(id);
        log.info("get issue by pic {} \n\n", allIssuesViewObject);
        return ResponseEntity.ok(allIssuesViewObject);
    }

    @PostMapping("/add-solution")
    public ResponseEntity<Issue> addSolutionToIssue(@RequestBody IssueSolveFormInput issueSolveFormInput) {
        log.info("add solution to issue {} \n\n", issueSolveFormInput);
        return ResponseEntity.ok(issueService.addSolutiontoIssue(issueSolveFormInput));
    }

    @GetMapping("/get-issue-by-createdBy/{id}")
    public ResponseEntity<List<AllIssueDisplayViewObject>> getIssueByCreatedBy(@PathVariable Long id) {
        List<AllIssueDisplayViewObject> allIssuesViewObject = issueService.getAllIssueByCreatedById(id);
        log.info("get issue by pic {} \n\n", allIssuesViewObject);
        return ResponseEntity.ok(allIssuesViewObject);
    }

    @GetMapping("/export-issue-pdf")
    public ResponseEntity<byte[]> export(@RequestParam Long issueId) {
        try {
            IssueReportDto issueReportDto = new IssueReportDto(issueService.getIssueById(issueId));
            Map<String, Object> issueParam = new HashMap<>();
            issueParam.put("issue", issueReportDto);

            byte[] pdfBytes = reportService.generatePdf(issueParam, "IssueReport");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "issue-report.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/convert-to-pdf")
    public ResponseEntity<byte[]> convertToPdf(@RequestBody HtmlRequest htmlRequest) {
        try {
            // Get the HTML content from the request
            String html = htmlRequest.getHtml();

            // Call your PDF conversion service (similar to the previous example)
            byte[] pdfBytes = reportService.convertHtmlToPdf(html);// Call your PDF conversion logic here

            // Respond with the PDF
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "attachment; filename=converted_page.pdf")
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }

    // Define a simple DTO for receiving HTML content in the request
    public static class HtmlRequest {
        private String html;

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }
    }
}
