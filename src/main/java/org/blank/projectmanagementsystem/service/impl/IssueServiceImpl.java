package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.IssueCreateFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.IssueViewObject;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.IssueService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    private final ClientRepository clientRepository;
    private final ResponsiblePartyRepository responsiblePartyRepository;

    @Override
    public Issue createIssue(IssueFormInput issueFormInput) {
        //take res party and save to database
        ResponsibleParty responsibleParty = new ResponsibleParty();
        responsibleParty.setProviders(new HashSet<>(userRepository.findAllById(issueFormInput.getResponsibleParty().getProviderIds())));
        if (issueFormInput.getResponsibleParty().getClientId() != null)
            responsibleParty.setClient(
                    clientRepository.findById(
                            issueFormInput.getResponsibleParty().getClientId()).orElse(null));

        //save res party to database
        responsibleParty = responsiblePartyRepository.save(responsibleParty);

        //map issueFormInput to issue
        Issue issue = new Issue();
        issue.setTitle(issueFormInput.getTitle());
        issue.setContent(issueFormInput.getContent());
        issue.setDirectCause(issueFormInput.getDirectCause());
        issue.setRootCause(issueFormInput.getRootCause());
        issue.setIssueCategory(issueCategoryRepository.findById(issueFormInput.getIssueCategory()).orElseThrow());
        issue.setIssuePlace(issuePlaceRepository.findById(issueFormInput.getIssuePlace()).orElseThrow());
        issue.setCreatedBy(getCurrentUser());
        issue.setPic(userRepository.findById(issueFormInput.getPic()).orElseThrow());
        issue.setResponsibleParty(responsibleParty);

        return issueRepository.save(issue);
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

    private User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username, username).orElse(null);
    }

    @Override
    public List<Issue> getAllIssue() {
        return issueRepository.findAll();
    }

}
