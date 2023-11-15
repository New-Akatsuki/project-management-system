package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Issue;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AllIssueDisplayViewObject {
    private Long id;
    private String title;
    private String category;
    private String place;
    private String responsibleParty;
    private boolean solved;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    public AllIssueDisplayViewObject(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.category = issue.getIssueCategory().getName();
        this.place = issue.getIssuePlace().getName();
        this.responsibleParty = issue.getResponsibleParty().getName();
        this.solved = issue.isSolved();
        this.createdAt = issue.getCreatedAt();
        this.modifiedAt = issue.getModifyAt();

    }

    public AllIssueDisplayViewObject(Long id, String title, String category, String place, String responsibleParty, boolean solved, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.place = place;
        this.responsibleParty = responsibleParty;
        this.solved = solved;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
