package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Issue;

import java.time.LocalDateTime;

@Data
@Builder
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
