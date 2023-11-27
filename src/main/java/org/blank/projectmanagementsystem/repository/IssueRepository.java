package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Issue;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT new org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject(i.id, i.title, i.issueCategory.name, i.issuePlace.name, i.responsibleParty.name,i.solved,i.createdAt,i.modifyAt) FROM Issue i WHERE i.pic.id = ?1")
    List<AllIssueDisplayViewObject> findByPicId(Long id);

    //get individual column of issue table
    @Query("SELECT new org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject(i.id, i.title, i.issueCategory.name, i.issuePlace.name, i.responsibleParty.name,i.solved,i.createdAt,i.modifyAt) FROM Issue i")
    List<AllIssueDisplayViewObject> getAllIssueDisplayViewObject();

    @Query("SELECT new org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject(i.id, i.title, i.issueCategory.name, i.issueCategory.id, i.issuePlace.name, i.issuePlace.id, i.responsibleParty.name,i.responsibleParty.id,i.solved,i.createdAt,i.modifyAt,i.content,i.directCause,i.rootCause,i.createdBy.name,i.createdBy.id,i.pic.name,i.pic.id) FROM Issue i WHERE i.id = ?1")
    List<IssueDetailsViewObject> getIssueDetailsViewObjectByIssueId(Long id);

    @Query("SELECT new org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject(i.correctiveAction, i.preventiveAction, i.impact, i.pic.id, i.solutionCreatedAt,i.solutionModifiedAt) FROM Issue i WHERE i.id = ?1")
    List<IssueSolutionViewObject> getIssueSolutionViewObjectByIssueId(Long id);

    @Query("SELECT new org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject(i.id, i.title, i.issueCategory.name, i.issuePlace.name, i.responsibleParty.name,i.solved,i.createdAt,i.modifyAt) FROM Issue i WHERE i.createdBy.id = ?1")
    List<AllIssueDisplayViewObject> findByCreatedByID(Long id);



}
