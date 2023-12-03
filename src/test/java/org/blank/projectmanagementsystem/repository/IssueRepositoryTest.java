package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
class IssueRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private IssueCategoryRepository issueCategoryRepository;

    @Autowired
    private IssuePlaceRepository issuePlaceRepository;

    @Autowired
    private ResponsiblePartyRepository responsiblePartyRepository;

    @Mock
    private IssueRepository issueRepository;

    @BeforeEach
    public void setupTestData() {
        MockitoAnnotations.openMocks(this);

        Department department = Department.builder()
                .id(1)
                .name("Test Department")
                .active(true)
                .build();
        departmentRepository.save(department);

        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("user@gmail.com")
                .username("user")
                .password("Khun1234")
                .role(Role.PM)
                .department(department)
                .active(true)
                .build();
        userRepository.save(user);

        IssueCategory issueCategory = IssueCategory.builder()
                .id(1L)
                .name("Test Issue Category")
                .build();
        issueCategoryRepository.save(issueCategory);

        IssuePlace issuePlace = IssuePlace.builder()
                .id(1L)
                .name("Test Issue Place")
                .build();
        issuePlaceRepository.save(issuePlace);

        ResponsibleParty responsibleParty = ResponsibleParty.builder()
                .id(1L)
                .name("Test Responsible Party")
                .build();
        responsiblePartyRepository.save(responsibleParty);

        Issue issue = Issue.builder()
                .id(1L)
                .title("Test Issue")
                .content("Test Content")
                .issueCategory(issueCategory)
                .issuePlace(issuePlace)
                .responsibleParty(responsibleParty)
                .solved(true)
                .rootCause("Test Root Cause")
                .directCause("Test Direct Cause")
                .correctiveAction("Test Corrective Action")
                .preventiveAction("Test Preventive Action")
                .impact("Test Impact")
                .pic(user)
                .createdAt(LocalDateTime.now())
                .createdBy(user)
                .modifyAt(LocalDateTime.now())
                .solutionCreatedAt(LocalDateTime.now())
                .solutionModifiedAt(LocalDateTime.now())
                .build();
        when(issueRepository.findByPicId(1L)).thenReturn(Collections.emptyList());
    }

    @Test
    public void testFindByPicId() {
        List<AllIssueDisplayViewObject> issues = issueRepository.findByPicId(1L);
        assertTrue(issues.size() > 0, "Expected at least one issue to be found");
    }
}
