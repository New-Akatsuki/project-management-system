package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Builder;
import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Issue;

import java.time.LocalDateTime;

@Data
@Builder
public class IssueSolutionViewObject {
    private String coAction;
    private String preAction;
    private String impact;
    private Long picId;
    private LocalDateTime solutionCreatedAt;
    private LocalDateTime solutionModifiedAt;

    public IssueSolutionViewObject(Issue issue) {
        this.coAction = issue.getCorrectiveAction();
        this.preAction = issue.getPreventiveAction();
        this.impact = issue.getImpact();
        this.picId = issue.getPic().getId();
    }

    public IssueSolutionViewObject(String coAction, String preAction, String impact, Long picId, LocalDateTime solutionCreatedAt, LocalDateTime solutionModifiedAt) {
        this.coAction = coAction;
        this.preAction = preAction;
        this.impact = impact;
        this.picId = picId;
        this.solutionCreatedAt = solutionCreatedAt;
        this.solutionModifiedAt = solutionModifiedAt;
    }
}
