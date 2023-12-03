package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueSolveFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;
import org.blank.projectmanagementsystem.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
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

    private IssueServiceImpl issueServiceImpl;

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
        Mockito.when(issueRepository.findByCreatedByID(123L)).thenReturn(mockIssueList);

        // Call the service method with the specific user ID
        List<AllIssueDisplayViewObject> result = issueServiceImpl.getAllIssueByCreatedById(123L);

        // Verify that the repository method was called with the correct argument
        verify(issueRepository, Mockito.times(1)).findByCreatedByID(123L);

        // Assert that the result matches the mock data
        assertEquals(mockIssueList, result);
    }


    @Test
    void testGetIssueDetailsById(){
        long issueId = 2L;

        IssueDetailsViewObject mockIssue =
                new IssueDetailsViewObject(issueId, "Title2", "Category2", 2L, "Place1", 1L, "Party1", 1L, false, LocalDateTime.now(), LocalDateTime.now(), "abcde", "fghi", "jklmno", "Susan", 2L, "James", 3L);

        // Mocking the repository method to return a list containing the mock data
        Mockito.when(issueRepository.getIssueDetailsViewObjectByIssueId(issueId))
                .thenReturn(Collections.singletonList(mockIssue));

        // Call the service method with a mocked user ID
        IssueDetailsViewObject result = issueServiceImpl.getIssueDetailsById(issueId);

        // Verify that the repository method was called with the correct argument
        verify(issueRepository, Mockito.times(1)).getIssueDetailsViewObjectByIssueId(issueId);

        // Assert that the result matches the mock data
        assertEquals(mockIssue, result);
    }


    @Test
    void testGetAllIssueByPic(){
        // Mock data for testing
        List<AllIssueDisplayViewObject> mockIssueList = Arrays.asList(
                new AllIssueDisplayViewObject(1L, "Title1", "Category1", "Place1", "Party1", true, LocalDateTime.now(), LocalDateTime.now()),
                new AllIssueDisplayViewObject(2L, "Title2", "Category2", "Place2", "Party2", false, LocalDateTime.now(), LocalDateTime.now())
                // Add more mock data as needed
        );

        // Mocking the repository method to return the mock data
        Mockito.when(issueRepository.findByPicId(123L)).thenReturn(mockIssueList);

        // Call the service method with the specific user ID
        List<AllIssueDisplayViewObject> result = issueServiceImpl.getAllIssueByPic(123L);

        // Verify that the repository method was called with the correct argument
        verify(issueRepository, Mockito.times(1)).findByPicId(123L);

        // Assert that the result matches the mock data
        assertEquals(mockIssueList, result);
    }

    @Test
    void testGetIssueSolutionById(){
        long issueId = 2L;
        IssueSolutionViewObject mockIssue =
                new IssueSolutionViewObject("abc","def","ghi",1L,LocalDateTime.now(),LocalDateTime.now());

        // Mocking the repository method to return a list containing the mock data
        Mockito.when(issueRepository.getIssueSolutionViewObjectByIssueId(issueId))
                .thenReturn(Collections.singletonList(mockIssue));

        // Call the service method with a mocked user ID
        IssueSolutionViewObject result = issueServiceImpl.getIssueSolutionById(issueId);

        // Verify that the repository method was called with the correct argument
        verify(issueRepository, Mockito.times(1)).getIssueSolutionViewObjectByIssueId(issueId);

        // Assert that the result matches the mock data
        assertEquals(mockIssue, result);
    }

    @Test
    void testCheckAndResizeImage() {
        // Given
        String originalContent = "<img src=\"example.jpg\">";
        String expectedContent = "<img style=\"width: 100%; height: auto;\" src=\"example.jpg\">";

        // When
        String result = issueServiceImpl.checkAndResizeImage(originalContent);

        // Then
        assertEquals(expectedContent, result);
    }

    @Test
    void testGetCurrentUser() {
        // Given
        String username = "testUser";
        User expectedUser = new User(/* your user details here */);

        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Authentication authenticationMock = Mockito.mock(Authentication.class);

        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(authenticationMock.getName()).thenReturn(username);
        Mockito.when(userRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(expectedUser));
        // When
        User result = issueServiceImpl.getCurrentUser();
        // Then
        assertEquals(expectedUser, result);
    }

    public class IssueTestData {
        public static Issue createFakeIssue() {
            User.builder()
                    .id(1L)
                    .name("John")
                    .email("john@gmail.com")
                    .password("Khun1234")
                    .role(Role.MEMBER)
                    .department(new Department(1, "abc", true))
                    .build();
            return Issue.builder()
                    .id(1L)
                    .title("Fake Issue Title")
                    .content("Fake issue content. Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                    .createdAt(LocalDateTime.now())
                    .modifyAt(LocalDateTime.now())
                    .solved(false)
                    .impact("Fake impact description")
                    .directCause("Fake direct cause description")
                    .rootCause("Fake root cause description")
                    .correctiveAction("Fake corrective action description")
                    .preventiveAction("Fake preventive action description")
                    .responsibleParty(new ResponsibleParty(1L, "party 1"))
                    .issuePlace(new IssuePlace(1L, "place 1"))
                    .issueCategory(new IssueCategory(1L, "category 1"))
                    .createdBy(User.builder().build())
                    .pic(User.builder().build())
                    .solutionCreatedAt(LocalDateTime.now())
                    .solutionModifiedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Test
    void testCreateIssue() {
        // Mocking SecurityContextHolder and userRepository
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        // Mocking userRepository response
        User mockedUser = User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.com")
                .password("Khun1234")
                .role(Role.MEMBER)
                .department(new Department(1, "abc", true))
                .build(); // You need to replace this with an actual User object
        when(userRepository.findByUsernameOrEmail("testUser", "testUser")).thenReturn(Optional.of(mockedUser));

        // Given
        IssueFormInput issueFormInput = new IssueFormInput(2L, "title", "content", "dirC", "rootC", 1L, 1L, 1L, 1L);
        Issue existingIssue = IssueTestData.createFakeIssue();
        User currentUser = User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.com")
                .password("Khun1234")
                .role(Role.MEMBER)
                .department(new Department(1, "abc", true))
                .build();

        // Mocking repository methods
        when(issueRepository.findById(anyLong())).thenReturn(Optional.of(existingIssue));
        when(issueRepository.save(any(Issue.class))).thenReturn(existingIssue);

        when(issueCategoryRepository.findById(anyLong())).thenReturn(Optional.of(new IssueCategory()));
        when(issuePlaceRepository.findById(anyLong())).thenReturn(Optional.of(new IssuePlace()));
        when(responsiblePartyRepository.findById(anyLong())).thenReturn(Optional.of(new ResponsibleParty()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(currentUser));

        // When
        Issue result = issueServiceImpl.createIssue(issueFormInput);

        // Then
        // Update the assertion based on your actual implementation and requirements
        assertEquals(issueFormInput.getTitle(), result.getTitle());
        assertEquals(issueFormInput.getContent(), result.getContent());
        // Add more assertions as needed for other properties
    }

    @Test
    void testCreateIssueWhenIssueIsNull() {
        // Mocking SecurityContextHolder and userRepository
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        // Mocking userRepository response
        User mockedUser = User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.com")
                .password("Khun1234")
                .role(Role.MEMBER)
                .department(new Department(1, "abc", true))
                .build(); // You need to replace this with an actual User object
        when(userRepository.findByUsernameOrEmail("testUser", "testUser")).thenReturn(Optional.of(mockedUser));
        Issue existingIssue = IssueTestData.createFakeIssue();
        // Given
        IssueFormInput issueFormInput = new IssueFormInput(null, existingIssue.getTitle(), existingIssue.getContent(), existingIssue.getDirectCause(), existingIssue.getRootCause(), 1L, 1L, 1L, 1L);

        User currentUser = User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.com")
                .password("Khun1234")
                .role(Role.MEMBER)
                .department(new Department(1, "abc", true))
                .build();

        // Mocking repository methods
        when(issueRepository.findById(anyLong())).thenReturn(Optional.of(existingIssue));
        when(issueRepository.save(any(Issue.class))).thenReturn(existingIssue);

        when(issueCategoryRepository.findById(anyLong())).thenReturn(Optional.of(new IssueCategory()));
        when(issuePlaceRepository.findById(anyLong())).thenReturn(Optional.of(new IssuePlace()));
        when(responsiblePartyRepository.findById(anyLong())).thenReturn(Optional.of(new ResponsibleParty()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(currentUser));

        // When
        Issue result = issueServiceImpl.createIssue(issueFormInput);

        // Then
        // Update the assertion based on your actual implementation and requirements
        assertEquals(issueFormInput.getTitle(), result.getTitle());
        assertEquals(issueFormInput.getContent(), result.getContent());
        // Add more assertions as needed for other properties
    }

    @Test
    void testAddSolutionToIssue() {
        // Given
        IssueSolveFormInput issueSolveFormInput = new IssueSolveFormInput(1L, "coAction", "preAction", "impact");
        Issue existingIssue = new Issue(); // Replace this with an actual Issue object
        existingIssue.setId(1L);
        existingIssue.setSolved(false); // Assuming the issue is not solved initially

        // Mocking repository methods
        when(issueRepository.findById(anyLong())).thenReturn(Optional.of(existingIssue));
        when(issueRepository.save(any(Issue.class))).thenReturn(existingIssue);

        // When
        Issue result = issueServiceImpl.addSolutiontoIssue(issueSolveFormInput);

        // Then
        assertEquals(1L, result.getId());
        assertTrue(result.isSolved());
        assertEquals("coAction", result.getCorrectiveAction());
        assertEquals("preAction", result.getPreventiveAction());
        assertEquals("impact", result.getImpact());

        // Assuming that the solution creation time is set when the issue is not solved
        if (!existingIssue.isSolved()) {
            assertNull(result.getSolutionCreatedAt());
        } else {
            assertNull(result.getSolutionModifiedAt());
        }

        // Verify that findById and save methods were called with the correct arguments
        verify(issueRepository).findById(1L);
        verify(issueRepository).save(any(Issue.class));
    }

    @Test
    void testAddSolutionToIssueModifyDate() {
        // Given
        IssueSolveFormInput issueSolveFormInput = new IssueSolveFormInput(1L, "coAction", "preAction", "impact");
        Issue existingIssue = new Issue(); // Replace this with an actual Issue object
        existingIssue.setId(1L);
        existingIssue.setSolved(false); // Assuming the issue is not solved initially
        existingIssue.setSolutionCreatedAt(LocalDateTime.now());
        // Mocking repository methods
        when(issueRepository.findById(anyLong())).thenReturn(Optional.of(existingIssue));
        when(issueRepository.save(any(Issue.class))).thenReturn(existingIssue);

        // When
        Issue result = issueServiceImpl.addSolutiontoIssue(issueSolveFormInput);
        // Then
        assertEquals(1L, result.getId());
        assertTrue(result.isSolved());
        assertEquals("coAction", result.getCorrectiveAction());
        assertEquals("preAction", result.getPreventiveAction());
        assertEquals("impact", result.getImpact());

        assertNotNull(result.getSolutionModifiedAt());
        // Verify that findById and save methods were called with the correct arguments
        verify(issueRepository).findById(1L);
        verify(issueRepository).save(any(Issue.class));
    }

}