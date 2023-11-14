package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ResPartyFormInput;
import org.blank.projectmanagementsystem.service.IssueService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IssueAPI {
    private final IssueService issueService;
    private final ProjectService projectService;

    @PostMapping("/member/create-category")
    public ResponseEntity<IssueCategory> createIssueCategory(@RequestBody IssueCategory issueCategory) {
        log.info("create issue category");
        return ResponseEntity.ok(issueService.createIssueCategory(issueCategory));
    }

    @GetMapping("/member/get-category")
    public ResponseEntity<List<IssueCategory>> getIssueCategory() {
        List<IssueCategory> issueCategories = issueService.getAllCategory();
        return ResponseEntity.ok(issueCategories);
    }

    @PostMapping("/member/create-place")
    public ResponseEntity<IssuePlace> createIssuePlace(@RequestBody IssuePlace issueplace) {
        log.info("create issue place");
        return ResponseEntity.ok(issueService.createIssuePlace(issueplace));
    }

    @GetMapping("/member/get-place")
    public ResponseEntity<List<IssuePlace>> getIssuePlace() {
        List<IssuePlace> issuePlaces = issueService.getAllPlace();
        return ResponseEntity.ok(issuePlaces);
    }

    @GetMapping("/get-responsible-party")
    public ResponseEntity<Map<String, List<Object>>> getResponsibleParty() {
        Map<String, List<Object>> responsibleParty = projectService.getUsersAndClientByOngoingProject();
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

    @GetMapping("/get-all-issue")
    public ResponseEntity<List<Issue>> getAllIssue() {
        List<Issue> issues = issueService.getAllIssue();
        log.info("get all issue {} \n\n", issues);
        return ResponseEntity.ok(issues);
    }
}
