//package org.blank.projectmanagementsystem.service;
//import org.blank.projectmanagementsystem.domain.entity.*;
//import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
//import org.blank.projectmanagementsystem.domain.formInput.IssueSolveFormInput;
//import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
//import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
//import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;
//import org.blank.projectmanagementsystem.repository.IssueRepository;
//import org.blank.projectmanagementsystem.repository.IssueCategoryRepository;
//import org.blank.projectmanagementsystem.repository.IssuePlaceRepository;
//import org.blank.projectmanagementsystem.repository.UserRepository;
//import org.blank.projectmanagementsystem.repository.ResponsiblePartyRepository;
//import org.blank.projectmanagementsystem.service.impl.IssueServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class IssueServiceTest {
//
//    @Mock
//    private IssueRepository issueRepository;
//
//    @Mock
//    private IssueCategoryRepository issueCategoryRepository;
//
//    @Mock
//    private IssuePlaceRepository issuePlaceRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ResponsiblePartyRepository responsiblePartyRepository;
//
//    @InjectMocks
//    private IssueServiceImpl issueService;
//
//
//    @Test
//    public void testAddSolutionToIssue() {
//        // Given
//        IssueSolveFormInput formInput = new IssueSolveFormInput();
//        formInput.setId(1L);
//        // Set other properties as needed
//
//        Issue existingIssue = new Issue();
//        existingIssue.setId(1L);
//        existingIssue.setSolved(false);
//
//        Issue updatedIssue = new Issue();
//        updatedIssue.setId(1L);
//        updatedIssue.setSolved(true);
//        // Set other properties based on your business logic
//
//        // When
//        when(issueRepository.findById(any())).thenReturn(Optional.of(existingIssue));
//        when(issueRepository.save(any(Issue.class))).thenReturn(updatedIssue);
//
//        Issue result = issueService.addSolutionToIssue(formInput);
//
//        // Then
//        assertEquals(updatedIssue, result);
//        // Add more assertions if needed
//    }
//
//    @Test
//    public void testGetAllIssues() {
//        // Given
//        Issue issue1 = new Issue();
//        Issue issue2 = new Issue();
//        // Set properties as needed
//
//        // When
//        when(issueRepository.findAll()).thenReturn(Arrays.asList(issue1, issue2));
//
//        List<Issue> allIssues = issueService.getAllIssues();
//
//        // Then
//        assertEquals(2, allIssues.size());
//        // Add more assertions if needed
//    }
//
//    @Test
//    public void testCreateIssueCategory() {
//        // Given
//        IssueCategory issueCategoryToSave = new IssueCategory();
//        issueCategoryToSave.setName("Test Category");
//
//        IssueCategory savedIssueCategory = new IssueCategory();
//        savedIssueCategory.setId(1L);
//        savedIssueCategory.setName("Test Category");
//
//        // When
//        when(issueCategoryRepository.save(issueCategoryToSave)).thenReturn(savedIssueCategory);
//
//        IssueCategory createdIssueCategory = issueService.createIssueCategory(issueCategoryToSave);
//
//        // Then
//        assertEquals("Test Category", createdIssueCategory.getName());
//        // Add more assertions if needed
//    }
//    @Test
//    public void testGetAllCategory() {
//        // Given
//        IssueCategory category1 = new IssueCategory();
//        IssueCategory category2 = new IssueCategory();
//        // Set properties as needed
//
//        // When
//        when(issueCategoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
//
//        List<IssueCategory> allCategories = issueService.getAllCategory();
//
//        // Then
//        assertEquals(2, allCategories.size());
//        // Add more assertions if needed
//    }
//    @Test
//    public void testCreateIssuePlace() {
//        // Given
//        IssuePlace issuePlaceToSave = new IssuePlace();
//        issuePlaceToSave.setName("Test Place");
//
//        IssuePlace savedIssuePlace = new IssuePlace();
//        savedIssuePlace.setId(1L);
//        savedIssuePlace.setName("Test Place");
//
//        // When
//        when(issuePlaceRepository.save(issuePlaceToSave)).thenReturn(savedIssuePlace);
//
//        IssuePlace createdIssuePlace = issueService.createIssuePlace(issuePlaceToSave);
//
//        // Then
//        assertEquals("Test Place", createdIssuePlace.getName());
//        // Add more assertions if needed
//    }
//    @Test
//    public void testGetAllPlace() {
//        // Given
//        IssuePlace place1 = new IssuePlace();
//        IssuePlace place2 = new IssuePlace();
//        // Set properties as needed
//
//        // When
//        when(issuePlaceRepository.findAll()).thenReturn(Arrays.asList(place1, place2));
//
//        List<IssuePlace> allPlaces = issueService.getAllPlace();
//
//        // Then
//        assertEquals(2, allPlaces.size());
//        // Add more assertions if needed
//    }
//    @Test
//    public void testGetAllIssueDisplayViewObject() {
//        // Given
//        AllIssueDisplayViewObject issue1 = new AllIssueDisplayViewObject();
//        AllIssueDisplayViewObject issue2 = new AllIssueDisplayViewObject();
//        // Set properties as needed
//
//        // When
//        when(issueRepository.getAllIssueDisplayViewObject()).thenReturn(Arrays.asList(issue1, issue2));
//
//        List<AllIssueDisplayViewObject> allIssues = issueService.getAllIssueDisplayViewObject();
//
//        // Then
//        assertEquals(2, allIssues.size());
//        // Add more assertions if needed
//    }
//    @Test
//    public void testCreateResponsibleParty() {
//        // Given
//        ResponsibleParty responsiblePartyToSave = new ResponsibleParty();
//        responsiblePartyToSave.setName("Test Responsible Party");
//
//        ResponsibleParty savedResponsibleParty = new ResponsibleParty();
//        savedResponsibleParty.setId(1L);
//        savedResponsibleParty.setName("Test Responsible Party");
//
//        // When
//        when(responsiblePartyRepository.save(responsiblePartyToSave)).thenReturn(savedResponsibleParty);
//
//        ResponsibleParty createdResponsibleParty = issueService.createResponsibleParty(responsiblePartyToSave);
//
//        // Then
//        assertEquals("Test Responsible Party", createdResponsibleParty.getName());
//        // Add more assertions if needed
//    }
//    @Test
//    public void testGetAllResponsibleParty() {
//        // Given
//        ResponsibleParty responsibleParty1 = new ResponsibleParty();
//        ResponsibleParty responsibleParty2 = new ResponsibleParty();
//        // Set properties as needed
//
//        // When
//        when(responsiblePartyRepository.findAll()).thenReturn(Arrays.asList(responsibleParty1, responsibleParty2));
//
//        List<ResponsibleParty> allResponsibleParties = issueService.getAllResponsibleParty();
//
//        // Then
//        assertEquals(2, allResponsibleParties.size());
//        // Add more assertions if needed
//    }
//
//
//
//
//
//
//
//    // Add more test methods for other service methods if needed
//}
