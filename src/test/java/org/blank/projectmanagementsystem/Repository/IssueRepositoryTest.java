package org.blank.projectmanagementsystem.Repository;
import org.blank.projectmanagementsystem.domain.entity.Issue;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;
import org.blank.projectmanagementsystem.repository.IssueRepository;
import org.blank.projectmanagementsystem.service.impl.IssueServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueRepositoryTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueServiceImpl issueService;  // Assuming you have a service using the repository

    @Test
    public void testFindByPicId() {
        // Given
        Long picId = 1L;
        List<AllIssueDisplayViewObject> mockIssues = List.of(
                new AllIssueDisplayViewObject(1L, "Title1", "Category1", "Place1", "Party1", true, LocalDateTime.now(), LocalDateTime.now()),
                new AllIssueDisplayViewObject(2L, "Title2", "Category2", "Place2", "Party2", false, LocalDateTime.now(), LocalDateTime.now())
                // Add more as needed
        );

        // When
        when(issueRepository.findByPicId(picId)).thenReturn(mockIssues);

        // Then
        List<AllIssueDisplayViewObject> result = issueService.getAllIssueByPic(picId);
        assertEquals(mockIssues, result);
    }

    @Test
    public void testGetAllIssueDisplayViewObject() {
        // Given
        List<AllIssueDisplayViewObject> mockIssues = List.of(
                new AllIssueDisplayViewObject(1L, "Title1", "Category1", "Place1", "Party1", true, LocalDateTime.now(), LocalDateTime.now()),
                new AllIssueDisplayViewObject(2L, "Title2", "Category2", "Place2", "Party2", false, LocalDateTime.now(), LocalDateTime.now())
                // Add more as needed
        );

        // When
        when(issueRepository.getAllIssueDisplayViewObject()).thenReturn(mockIssues);

        // Then
        List<AllIssueDisplayViewObject> result = issueService.getAllIssueDisplayViewObject();
        assertEquals(mockIssues, result);
    }
    @Test
    public void testGetIssueDetailsViewObjectByIssueId() {
        // Given
        Long issueId = 1L;
        List<IssueDetailsViewObject> mockIssues = List.of(
                new IssueDetailsViewObject(1L, "Title1", "Category1", 1L, "Place1", 1L, "Party1", 1L, true, LocalDateTime.now(), LocalDateTime.now(), "Content1", "DirectCause1", "RootCause1", "CreatedBy1", 1L, "Pic1", 1L),
                new IssueDetailsViewObject(2L, "Title2", "Category2", 2L, "Place2", 2L, "Party2", 2L, false, LocalDateTime.now(), LocalDateTime.now(), "Content2", "DirectCause2", "RootCause2", "CreatedBy2", 2L, "Pic2", 2L)
                // Add more as needed
        );

        // When
        when(issueRepository.getIssueDetailsViewObjectByIssueId(issueId)).thenReturn(mockIssues);

        // Then
        List<IssueDetailsViewObject> result = issueService.getIssueDetailsViewObjectByIssueId(issueId);
        assertEquals(mockIssues, result);
    }
    @Test
    public void testGetIssueSolutionViewObjectByIssueId() {
        // Given
        Long issueId = 1L;
        List<IssueSolutionViewObject> mockIssues = List.of(
                new IssueSolutionViewObject("CorrectiveAction1", "PreventiveAction1", "Impact1", 1L, LocalDateTime.now(), LocalDateTime.now()),
                new IssueSolutionViewObject("CorrectiveAction2", "PreventiveAction2", "Impact2", 2L, LocalDateTime.now(), LocalDateTime.now())
                // Add more as needed
        );

        // When
        when(issueRepository.getIssueSolutionViewObjectByIssueId(issueId)).thenReturn(mockIssues);

        // Then
        List<IssueSolutionViewObject> result = issueService.getIssueSolutionViewObjectByIssueId(issueId);
        assertEquals(mockIssues, result);
    }



    // Add more test methods as needed for other repository methods
}

