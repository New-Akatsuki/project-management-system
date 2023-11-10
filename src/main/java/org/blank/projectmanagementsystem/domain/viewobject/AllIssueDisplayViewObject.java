package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Issue;

import java.time.LocalDateTime;

@Data
public class AllIssueDisplayViewObject {
    private Long id;
    private String title;
    private String category;
    private String place;
    private boolean solved;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AllIssueDisplayViewObject(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.category = issue.getIssueCategory().getName();
        this.place = issue.getIssuePlace().getName();
        this.solved = issue.isSolved();
        this.createdAt = issue.getCreatedAt();
        this.modifiedAt = issue.getModifyAt();
    }
}
