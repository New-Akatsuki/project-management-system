package org.blank.projectmanagementsystem.Service.impl;

import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.blank.projectmanagementsystem.domain.entity.IssuePlace;
import org.blank.projectmanagementsystem.domain.entity.ResponsibleParty;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.IssueService;
import org.blank.projectmanagementsystem.service.impl.IssueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IssueServiceImplTest {

    @Mock
    private IssueCategoryRepository issueCategoryRepository;

    @Mock
    private IssuePlaceRepository issuePlaceRepository;

    @Mock
    private ResponsiblePartyRepository responsiblePartyRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;


    private IssueService issueServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        issueServiceImpl = new IssueServiceImpl(
                issueRepository,
                issueCategoryRepository,
                issuePlaceRepository,
                userRepository,
                responsiblePartyRepository);
    }

    @Test
    void testCreateIssueCategory() {
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setName("test");
        when(issueCategoryRepository.save(issueCategory)).thenReturn(new IssueCategory(1L, "test"));
        IssueCategory result = issueServiceImpl.createIssueCategory(issueCategory);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
    }

    @Test
    void testCreateIssuePlace() {
        IssuePlace issuePlace = new IssuePlace();
        issuePlace.setName("test");
        when(issuePlaceRepository.save(issuePlace)).thenReturn(new IssuePlace(1L, "test"));
        IssuePlace result = issueServiceImpl.createIssuePlace(issuePlace);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
    }

    @Test
    void testCreateResponsibleParty() {
        ResponsibleParty responsibleParty = new ResponsibleParty();
        responsibleParty.setName("test");
        when(responsiblePartyRepository.save(responsibleParty)).thenReturn(new ResponsibleParty(1L, "test"));
        ResponsibleParty result = issueServiceImpl.createResponsibleParty(responsibleParty);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
    }

    @Test
    void testGetAllCategory() {
        // Arrange
        List<IssueCategory> expectedCategories = Arrays.asList(
                new IssueCategory(1L, "Category1"),
                new IssueCategory(2L, "Category2")
                // Add more categories as needed
        );
        // Mocking - when someone calls issueCategoryRepository.findAll, return the expected list
        when(issueCategoryRepository.findAll()).thenReturn(expectedCategories);
        // Act
        List<IssueCategory> result = issueServiceImpl.getAllCategory();
        // Check if the result matches the expected values
        assertEquals(expectedCategories.size(), result.size());
        assertEquals(expectedCategories, result);
    }

    @Test
    void testGetAllPlace() {
        // Arrange
        List<IssuePlace> expectedPlaces = Arrays.asList(
                new IssuePlace(1L, "Place1"),
                new IssuePlace(2L, "Place2")
                // Add more categories as needed
        );
        // Mocking - when someone calls issueCategoryRepository.findAll, return the expected list
        when(issuePlaceRepository.findAll()).thenReturn(expectedPlaces);
        // Act
        List<IssuePlace> result = issueServiceImpl.getAllPlace();
        // Check if the result matches the expected values
        assertEquals(expectedPlaces.size(), result.size());
        assertEquals(expectedPlaces, result);
    }

    @Test
    void testGetAllResponsibleParty() {
        // Arrange
        List<ResponsibleParty> expectedParties = Arrays.asList(
                new ResponsibleParty(1L, "Place1"),
                new ResponsibleParty(2L, "Place2")
                // Add more categories as needed
        );
        // Mocking - when someone calls issueCategoryRepository.findAll, return the expected list
        when(responsiblePartyRepository.findAll()).thenReturn(expectedParties);
        // Act
        List<ResponsibleParty> result = issueServiceImpl.getAllResponsibleParty();
        // Check if the result matches the expected values
        assertEquals(expectedParties.size(), result.size());
        assertEquals(expectedParties, result);
    }

    @Test
    void testGetAllIssueDisplayViewObject() {
        List<AllIssueDisplayViewObject> expectedIssueDisplay = Arrays.asList(
                new AllIssueDisplayViewObject(
                        1L,
                        "testTitle",
                        "testCategory",
                        "testPlace",
                        "testResParty",
                        true,
                        LocalDateTime.parse("2023-11-17T15:02:31.105665"),
                        LocalDateTime.parse("2023-12-17T15:02:31.105665")
                ),
                new AllIssueDisplayViewObject(
                        2L,
                        "testTitle2",
                        "testCategory2",
                        "testPlace2",
                        "testResParty2",
                        false,
                        LocalDateTime.parse("2023-11-17T15:02:31.105665"),
                        LocalDateTime.parse("2023-12-17T15:02:31.105665")
                )
        );
        // Mocking - when someone calls issueCategoryRepository.findAll, return the expected list
        when(issueRepository.getAllIssueDisplayViewObject()).thenReturn(expectedIssueDisplay);
        // Act
        List<AllIssueDisplayViewObject> result = issueServiceImpl.getAllIssueDisplayViewObject();
        // Check if the result matches the expected values
        assertEquals(expectedIssueDisplay.size(), result.size());
        assertEquals(expectedIssueDisplay, result);
    }

    @Test
    void testGetAllIssueByCreatedById() {
        // Mock data for testing
        List<AllIssueDisplayViewObject> mockIssueList = Arrays.asList(
                new AllIssueDisplayViewObject(1L, "Title1", "Category1", "Place1", "Party1", true, LocalDateTime.now(), LocalDateTime.now()),
                new AllIssueDisplayViewObject(2L, "Title2", "Category2", "Place2", "Party2", false, LocalDateTime.now(), LocalDateTime.now())
                // Add more mock data as needed
        );
        // Mocking the repository method to return the mock data
        Mockito.when(issueRepository.findByCreatedByID(anyLong())).thenReturn(mockIssueList);
        // Call the service method with a mocked user ID
        List<AllIssueDisplayViewObject> result = issueServiceImpl.getAllIssueByCreatedById(123L);
        // Verify that the repository method was called with the correct argument
        Mockito.verify(issueRepository, Mockito.times(1)).findByCreatedByID(123L);
        // Assert that the result matches the mock data
        assertEquals(mockIssueList, result);
    }
}