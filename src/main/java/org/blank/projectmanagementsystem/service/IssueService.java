package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.blank.projectmanagementsystem.domain.entity.IssuePlace;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.IssueCreateFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.IssueViewObject;

import java.util.List;

public interface IssueService {
    IssueViewObject createIssue(IssueCreateFormInput issueCreateFormInput);
    IssueCategory createIssueCategory(IssueCategory issueCategory);
    List<IssueCategory> getAllCategory();
    IssuePlace createIssuePlace(IssuePlace issuePlace);
    List<IssuePlace> getAllPlace();
    List<User> getAllUser();
}
