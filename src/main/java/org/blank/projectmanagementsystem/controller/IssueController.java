package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.blank.projectmanagementsystem.domain.entity.IssuePlace;
import org.blank.projectmanagementsystem.domain.formInput.IssueCreateFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.IssueViewObject;
import org.blank.projectmanagementsystem.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IssueController {
    private final IssueService issueService;

    @PostMapping("/issue-create")
    public ResponseEntity<IssueViewObject> createIssue(@RequestBody IssueCreateFormInput issueCreateFormInput) {
        log.info("create issue");
        return ResponseEntity.ok( issueService.createIssue(issueCreateFormInput));
    }

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
}
