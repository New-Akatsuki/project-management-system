package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueSolveFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;

import java.util.List;

public interface IssueService {
    Issue createIssue(IssueFormInput issueFormInput);
    IssueCategory createIssueCategory(IssueCategory issueCategory);
    List<IssueCategory> getAllCategory();
    IssuePlace createIssuePlace(IssuePlace issuePlace);
    List<IssuePlace> getAllPlace();
    List<User> getAllUser();
    List<Issue> getAllIssue();
    List<AllIssueDisplayViewObject> getAllIssueDisplayViewObject();
    ResponsibleParty createResponsibleParty(ResponsibleParty responsibleParty);
    List<ResponsibleParty> getAllResponsibleParty();
    IssueDetailsViewObject getIssueDetailsById(Long id);
    IssueSolutionViewObject getIssueSolutionById(Long id);
    List<AllIssueDisplayViewObject> getAllIssueByPic(Long id);
    Issue addSolutiontoIssue(IssueSolveFormInput issueSolveFormInput);

    List<AllIssueDisplayViewObject> getAllIssueByCreatedById(Long id);
}
