package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.blank.projectmanagementsystem.domain.entity.IssuePlace;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.IssueCreateFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.IssueViewObject;
import org.blank.projectmanagementsystem.repository.IssueCategoryRepository;
import org.blank.projectmanagementsystem.repository.IssuePlaceRepository;
import org.blank.projectmanagementsystem.repository.IssueRepository;
import org.blank.projectmanagementsystem.service.IssueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final IssueCategoryRepository issueCategoryRepository;
    private final IssuePlaceRepository issuePlaceRepository;
    @Override
    public IssueViewObject createIssue(IssueCreateFormInput issueCreateFormInput) {
//        return issueRepository.save(issueCreateFormInput.toIssue()).toIssueViewObject();
        return null;
    }
    @Override
    public IssueCategory createIssueCategory(IssueCategory issueCategory) {
        return issueCategoryRepository.save(new IssueCategory(null, issueCategory.getName()));
    }

    @Override
    public List<IssueCategory> getAllCategory() {
        return issueCategoryRepository.findAll();
    }

    @Override
    public IssuePlace createIssuePlace(IssuePlace issuePlace) {
        return issuePlaceRepository.save(new IssuePlace(null, issuePlace.getName()));
    }

    @Override
    public List<IssuePlace> getAllPlace() {
        return issuePlaceRepository.findAll();
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }


}
