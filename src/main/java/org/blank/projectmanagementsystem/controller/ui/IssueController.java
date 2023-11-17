package org.blank.projectmanagementsystem.controller.ui;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.formInput.IssueCreateFormInput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class IssueController {

    @GetMapping("/issues")
    public String allIssue() {
        return "issue-all-display";
    }

    @GetMapping("/issues/new")
    public String issueCreate() {
        return "issue-create";
    }

    @GetMapping("/issues/pic")
    public String issuePicView() {
        return "issue-pic-display";
    }

    @GetMapping("/issues/my-issues")
    public String issueCreatorView() {
        return "issue-creator-display";
    }

    @GetMapping("/issues/{id}/details")
    public String issueDetailsView(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("id", id);
        return "issue-details";
    }

    @GetMapping("/issues/{id}/edit")
    public String editIssue(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("id", id);
        return "issue-edit";
    }
}
