package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Issue;

import java.time.LocalDateTime;
@Data
public class IssueDetailsViewObject {
    private Long id;
    private String title;
    private String category;
    private Long categoryId;
    private String place;
    private Long placeId;
    private String responsibleParty;
    private Long responsiblePartyId;
    private boolean solved;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String content;
    private String directCause;
    private String rootCause;
    private String createdBy;
    private Long createdByUserId;
    private String pic;
    private Long picId;

    public IssueDetailsViewObject(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.category = issue.getIssueCategory().getName();
        this.place = issue.getIssuePlace().getName();
        this.responsibleParty = issue.getResponsibleParty().getName();
        this.solved = issue.isSolved();
        this.createdAt = issue.getCreatedAt();
        this.modifiedAt = issue.getModifyAt();
        this.content = issue.getContent();
        this.directCause = issue.getDirectCause();
        this.rootCause = issue.getRootCause();
        this.createdBy = issue.getCreatedBy().getName();
        this.pic = issue.getPic().getName();
        this.picId = issue.getPic().getId();
    }

    public IssueDetailsViewObject(Long id, String title, String category, Long categoryId, String place, Long placeId, String responsibleParty, Long responsiblePartyId, boolean solved, LocalDateTime createdAt, LocalDateTime modifiedAt, String content, String directCause, String rootCause, String createdBy, Long createdByUserId, String pic, Long picId) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.categoryId = categoryId;
        this.place = place;
        this.placeId = placeId;
        this.responsibleParty = responsibleParty;
        this.responsiblePartyId = responsiblePartyId;
        this.solved = solved;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.content = content;
        this.directCause = directCause;
        this.rootCause = rootCause;
        this.createdBy = createdBy;
        this.createdByUserId = createdByUserId;
        this.pic = pic;
        this.picId = picId;
    }
}
