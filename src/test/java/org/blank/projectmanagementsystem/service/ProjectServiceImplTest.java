package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectEditViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.repository.*;
import org.blank.projectmanagementsystem.service.UserService;
import org.blank.projectmanagementsystem.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SystemOutlineRepository systemOutlineRepository;

    @Mock
    private ArchitectureRepository architectureRepository;

    @Mock
    private DeliverableRepository deliverableRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AutoCloseable autoCloseable;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        String pmUsername = "pmUser";
        autoCloseable =  MockitoAnnotations.openMocks(this);
        // Mocking SecurityContextHolder
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(pmUsername);

        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testSaveProject() {
        String pmUsername = "pmUser";
        // Given
        User currentUser = User.builder().department(new Department()).build();
        User projectManager = User.builder().department(Department.builder().name("d1").build()).build();
        Client client = new Client();
        List<Long> contractMembersIds = Collections.singletonList(1L);
        List<Long> focMembersIds = Collections.singletonList(2L);
        List<Long> systemOutlineIds = Collections.singletonList(3L);
        List<Long> architectureIds = Collections.singletonList(4L);
        List<Long> deliverableIds = Collections.singletonList(5L);

        ProjectFormInput projectFormInput = new ProjectFormInput();
        projectFormInput.setClient(1L);
        projectFormInput.setContractMembers(contractMembersIds);
        projectFormInput.setFocMembers(focMembersIds);
        projectFormInput.setSystemOutlines(systemOutlineIds);
        projectFormInput.setArchitectureOutlines(architectureIds);
        projectFormInput.setDeliverables(deliverableIds);
        projectFormInput.setStartDate(LocalDate.now());
        projectFormInput.setEndDate(LocalDate.now());

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(pmUsername);
        when(userRepository.findByUsernameOrEmail(pmUsername, pmUsername)).thenReturn(Optional.of(projectManager));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().build())); // Mock user retrieval for contract members
        when(userRepository.findById(2L)).thenReturn(Optional.of(User.builder().build())); // Mock user retrieval for foc members
        when(systemOutlineRepository.findById(3L)).thenReturn(Optional.of(new SystemOutline()));
        when(architectureRepository.findById(4L)).thenReturn(Optional.of(new Architecture()));
        when(deliverableRepository.findById(5L)).thenReturn(Optional.of(new Deliverable()));

        Project savedProject = Project.builder().build();
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        // When
        Project result = projectService.saveProject(projectFormInput);

        // Then
        assertEquals(savedProject, result);
        verify(projectRepository, times(1)).save(any(Project.class));
    }


    @Test
    void testGetAllProjectsByPMO() {
        // Arrange
        User pmoUser = new User();
        pmoUser.setRole(Role.PMO);

        // Mocking the user role
        when(userService.getCurrentUser()).thenReturn(pmoUser);

        // Mocking projects for PMO

        when(projectRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<ProjectListViewObject> result = projectService.getAllProjects();

        // Assert
        assertEquals(0, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProjectsByDH() {
        // Arrange
        User pmoUser = new User();
        pmoUser.setRole(Role.DH);

        // Mocking the user role
        when(userService.getCurrentUser()).thenReturn(pmoUser);

        // Mocking projects for PMO

        when(projectRepository.findAllByDepartment(any())).thenReturn(new ArrayList<>());

        // Act
        List<ProjectListViewObject> result = projectService.getAllProjects();

        // Assert
        assertEquals(0, result.size());
        verify(projectRepository, times(1)).findAllByDepartment(any(
        ));
    }

    @Test
    void testGetAllProjectsByPM() {
        // Arrange
        User pmoUser = new User();
        pmoUser.setRole(Role.PM);

        // Mocking the user role
        when(userService.getCurrentUser()).thenReturn(pmoUser);

        // Mocking projects for PMO

        when(projectRepository.findAllByProjectManager(pmoUser)).thenReturn(new ArrayList<>());

        // Act
        List<ProjectListViewObject> result = projectService.getAllProjects();

        // Assert
        assertEquals(0, result.size());
        verify(projectRepository, times(1)).findAllByProjectManager(pmoUser);
    }

    @Test
    void testGetAllProjectsByMember() {
        // Arrange
        User pmoUser = new User();
        pmoUser.setRole(Role.MEMBER);

        // Mocking the user role
        when(userService.getCurrentUser()).thenReturn(pmoUser);

        // Mocking projects for PMO

        when(projectRepository.findAllOngoingProjectsByUserInMembers(any())).thenReturn(new ArrayList<>());

        // Act
        List<ProjectListViewObject> result = projectService.getAllProjects();

        // Assert
        assertEquals(0, result.size());
        verify(projectRepository, times(1)).findAllOngoingProjectsByUserInMembers(any());
    }

    // Example test for getProjectById
    @Test
    void testGetProjectById() {
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.ONGOING)
                // Set other properties for the project
                .build();
        // Given
        when(projectRepository.getReferenceById(anyLong()))
                .thenReturn(p);

        // When
        ProjectViewObject result = projectService.getProjectById(1L);

        // Then
        assertEquals(p.getProjectManager().getName(),result.getProjectManager());
    }

    @Test
    void testGetReferenceById() {
        // Given
        Long projectId = 1L;
        Project expectedProject = new Project(); // create a dummy project object
        when(projectRepository.getReferenceById(projectId)).thenReturn(expectedProject);

        // When
        Project result = projectService.getReferenceById(projectId);

        // Then
        assertEquals(expectedProject, result);
    }

    @Test
    void testGetProjectMembers() {
        // Given
        Long projectId = 1L;
        Project project = new Project(); // create a dummy project object
        User contractMember1 = User.builder().id(1L).build(); // create dummy user objects
        User contractMember2 = User.builder().id(2L).build();
        User focMember1 = new User();
        User projectManager = new User();

        project.setContractMembers(Set.of(contractMember1, contractMember2));
        project.setFocMembers(Set.of(focMember1));
        project.setProjectManager(projectManager);

        when(projectRepository.findById(projectId)).thenReturn(java.util.Optional.of(project));

        // When
        List<User> result = projectService.getProjectMembers(projectId);

        // Then
        assertEquals(4, result.size()); // assuming there are 2 contract members, 1 FOC member, and 1 project manager
        assertTrue(result.containsAll(Arrays.asList(contractMember1, contractMember2, focMember1, projectManager)));
    }


    // Example test for toggleProjectStatus
    @Test
    void testToggleProjectStatusReturnOngoing() {
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.PENDING)
                .build();
        // Given
        when(projectRepository.getReferenceById(1L)).thenReturn(p);
        when(projectRepository.save(p)).thenReturn(p);
        when(taskRepository.findAllByProjectId(1L)).thenReturn(List.of(
                Task.builder().status(true).build(),
                Task.builder().status(false).build()
                ));
//        when(projectService.countProgress(1L)).thenReturn(50);

        // When
        String result = projectService.toggleProjectStatus(1L);

        // Then
        assertEquals(ProjectStatus.ONGOING.name(),result);
    }

    @Test
    void testToggleProjectStatusReturnFinished() {
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.PENDING)
                .build();
        // Given
        when(projectRepository.getReferenceById(1L)).thenReturn(p);
        when(projectRepository.save(p)).thenReturn(p);
        when(taskRepository.findAllByProjectId(1L)).thenReturn(List.of(
                Task.builder().status(true).build(),
                Task.builder().status(true).build()
        ));
//        when(projectService.countProgress(1L)).thenReturn(50);

        // When
        String result = projectService.toggleProjectStatus(1L);

        // Then
        assertEquals(ProjectStatus.FINISHED.name(),result);
    }

    @Test
    void testToggleProjectStatusReturnPending() {
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.ONGOING)
                .build();
        // Given
        when(projectRepository.getReferenceById(1L)).thenReturn(p);
        when(projectRepository.save(p)).thenReturn(p);

        // When
        String result = projectService.toggleProjectStatus(1L);

        // Then
        assertEquals(ProjectStatus.PENDING.name(),result);
    }
    @Test
    void testGetUsersByOngoingProject() {
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.ONGOING)
                .build();
        // Given
        User currentUser = new User(); // create a dummy user object
        when(userService.getCurrentUser()).thenReturn(currentUser);

        Project ongoingProject1 = Project.builder().id(1L).build(); // create dummy ongoing projects
        Project ongoingProject2 = Project.builder().id(2L).build();

        when(projectRepository.findAllProjectsByUserInMembersAndStatus(currentUser, ProjectStatus.ONGOING))
                .thenReturn(Optional.of(List.of(ongoingProject1, ongoingProject2)));

        User projectMember1 = User.builder().id(1L).build(); // create dummy project members
        User projectMember2 = User.builder().id(2L).build();
        User projectMember3 = User.builder().id(3L).build();

        // Assuming getProjectMembers is working correctly and returns distinct users
        when(projectRepository.findById(1L)).thenReturn(Optional.of(p));
//        when(projectService.getProjectMembers(1L));

        // When
        List<User> result = projectService.getUsersByOngoingProject();

        // Then
        assertEquals(1, result.size()); // assuming there are 3 distinct project members from the ongoing projects
    }
    @Test
    public void testGetUsersAndClientByOngoingProject() {
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.ONGOING)
                .build();
        // Mocking current user and projects
         when(projectRepository.findAllProjectsByUserInMembersAndStatus(any(),any()))
                .thenReturn(Optional.of(List.of(p)));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(p));

        // Calling the method to be tested
        Map<String, List<Object>> result = projectService.getUsersAndClientByOngoingProject();

        // Assertions
        assertEquals(2, result.size());
    }

    @Test
    public void testGetProjectsByDepartment() {
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.ONGOING)
                .build();
        // Mocking data
        Integer departmentId = 1;
        List<Project> projects = new ArrayList<>();
        projects.add(p);
        when(projectRepository.findByDepartmentId(departmentId)).thenReturn(projects);

        // Calling the method to be tested
        List<ProjectViewObject> result = projectService.getProjectsByDepartment(departmentId);

        // Verifying the interactions
        verify(projectRepository, times(1)).findByDepartmentId(departmentId);

        // Assertions
        assertEquals(projects.size(), result.size());
        // Add more specific assertions based on your actual implementation
    }

    @Test
    public void testGetProject() {
        // Mocking data
        long projectId = 1L;
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.ONGOING)
                .build();
        when(projectRepository.getReferenceById(projectId)).thenReturn(p);

        // Calling the method to be tested
        Project result = projectService.getProject(projectId);

        // Verifying the interactions
        verify(projectRepository, times(1)).getReferenceById(projectId);

        // Assertions
        assertNotNull(result);
        assertEquals(projectId, result.getId());
        // Add more specific assertions based on your actual implementation
    }

    @Test
    public void testGetProjectByID() {
        // Mocking data
        Long projectId = 1L;
        Project p = Project.builder()
                .id(1L)
                .name("Project ABC")
                .background("Project background description")
                .objective("Project objective description")
                .duration("3 months")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .projectManager(User.builder().name("pm").build())
                .department(Department.builder().name("de1").build())
                .contractMembers(new HashSet<>())
                .focMembers(new HashSet<>())  // Assuming focMembers is also part of the project
                .architectures(new HashSet<>())  // Assuming architectures is also part of the project
                .systemOutlines(new HashSet<>())
                .deliverables(new HashSet<>())  // Assuming deliverables is also part of the project
                .amounts(List.of())  // Assuming amounts is also part of the project
                .reviewCounts(List.of())  // Assuming reviewCounts is also part of the project
                .client(new Client())  // Assuming client is also part of the project
                .status(ProjectStatus.ONGOING)
                .build();
        when(projectRepository.getReferenceById(projectId)).thenReturn(p);

        // Calling the method to be tested
        ProjectEditViewObject result = projectService.getProjectByID(projectId);

        // Verifying the interactions
        verify(projectRepository, times(1)).getReferenceById(projectId);

        // Assertions
        assertNotNull(result);
        // Add more specific assertions based on your actual implementation
    }
}
