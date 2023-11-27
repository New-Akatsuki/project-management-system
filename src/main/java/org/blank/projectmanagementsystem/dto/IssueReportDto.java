package org.blank.projectmanagementsystem.dto;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Issue;

@Data
public class IssueReportDto {
    private String pic;
    private String responsibleParty;
    private String category;
    private String place;
    private String content;
    private String directCause;
    private String rootCause;
    private String cAction;
    private String pAction;
    private String impact;

    public IssueReportDto(Issue issue){
        this.pic = issue.getPic().getName();
        this.responsibleParty = issue.getResponsibleParty().getName();
        this.category = issue.getIssueCategory().getName();
        this.place = issue.getIssuePlace().getName();
        this.content = issue.getContent();
        this.directCause = issue.getDirectCause();
        this.rootCause = issue.getRootCause();
        this.cAction = issue.getCorrectiveAction();
        this.pAction = issue.getPreventiveAction();
        this.impact = issue.getImpact();
    }
}
