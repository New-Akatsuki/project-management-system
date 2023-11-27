package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.IssueCreateFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueSolveFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueViewObject;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.IssueService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final IssueCategoryRepository issueCategoryRepository;
    private final IssuePlaceRepository issuePlaceRepository;
    private final UserRepository userRepository;
    private final ResponsiblePartyRepository responsiblePartyRepository;

    private User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username, username).orElse(null);
    }

    @Override
    public Issue createIssue(IssueFormInput issueFormInput) {
        Issue issue = null;
        if(issueFormInput.getId()!=null){
            issue = issueRepository.findById(issueFormInput.getId()).orElseThrow();
            issue.setModifyAt(LocalDateTime.now());
        }else{
            issue = new Issue();
        }
        //map issueFormInput to issue
        issue.setTitle(issueFormInput.getTitle());
        issue.setContent(checkAndResizeImage(issueFormInput.getContent()));
        issue.setDirectCause(checkAndResizeImage(issueFormInput.getDirectCause()));
        issue.setRootCause(checkAndResizeImage(issueFormInput.getRootCause()));
        issue.setIssueCategory(issueCategoryRepository.findById(issueFormInput.getIssueCategory()).orElseThrow());
        issue.setIssuePlace(issuePlaceRepository.findById(issueFormInput.getIssuePlace()).orElseThrow());
        issue.setCreatedBy(getCurrentUser());
        issue.setPic(userRepository.findById(issueFormInput.getPic()).orElseThrow());
        issue.setResponsibleParty(responsiblePartyRepository.findById(issueFormInput.getResponsibleParty()).orElseThrow());
        return issueRepository.save(issue);
    }

    private String checkAndResizeImage(String content) {
        return content.replace("<img", "<img style=\"width: 100%; height: auto;\"");
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public List<Issue> getAllIssue() {
        return issueRepository.findAll();
    }



    @Override
    public IssueDetailsViewObject getIssueDetailsById(Long id) {
        return issueRepository.getIssueDetailsViewObjectByIssueId(id).get(0);
    }

    @Override
    public IssueSolutionViewObject getIssueSolutionById(Long id) {
        return issueRepository.getIssueSolutionViewObjectByIssueId(id).get(0);
    }

    @Override
    public List<AllIssueDisplayViewObject> getAllIssueByPic(Long id) {
        return issueRepository.findByPicId(id);
    }

    @Override
    public Issue addSolutiontoIssue(IssueSolveFormInput issueSolveFormInput) {
        Issue issue = issueRepository.findById(issueSolveFormInput.getId()).orElseThrow();
        issue.setSolved(true);
        issue.setCorrectiveAction(checkAndResizeImage(issueSolveFormInput.getCoAction()));
        issue.setPreventiveAction(checkAndResizeImage(issueSolveFormInput.getPreAction()));
        issue.setImpact(checkAndResizeImage(issueSolveFormInput.getImpact()));
        if(issue.getSolutionCreatedAt() == null){
            issue.setSolutionCreatedAt(LocalDateTime.now());
        }else{
            issue.setSolutionModifiedAt(LocalDateTime.now());
        }
        return issueRepository.save(issue);
    }





    //Done Testing
    @Override
    public IssueCategory createIssueCategory(IssueCategory issueCategory) {
        return issueCategoryRepository.save(new IssueCategory(null, issueCategory.getName()));
    }
    //Done Testing
    @Override
    public List<IssueCategory> getAllCategory() {
        return issueCategoryRepository.findAll();
    }
    //Done Testing
    @Override
    public IssuePlace createIssuePlace(IssuePlace issuePlace) {
        return issuePlaceRepository.save(new IssuePlace(null, issuePlace.getName()));
    }
    //Done Testing
    @Override
    public List<IssuePlace> getAllPlace() {
        return issuePlaceRepository.findAll();
    }
    //Done Testing
    @Override
    public ResponsibleParty createResponsibleParty(ResponsibleParty responsibleParty) {
        return responsiblePartyRepository.save(new ResponsibleParty(null, responsibleParty.getName()));
    }
    //Done Testing
    @Override
    public List<ResponsibleParty> getAllResponsibleParty() {
        return responsiblePartyRepository.findAll();
    }
    //Done Testing
    @Override
    public List<AllIssueDisplayViewObject> getAllIssueDisplayViewObject() {
        return issueRepository.getAllIssueDisplayViewObject();
    }
    //Done Testing
    @Override
    public List<AllIssueDisplayViewObject> getAllIssueByCreatedById(Long id) {
        return issueRepository.findByCreatedByID(id);
    }

}
