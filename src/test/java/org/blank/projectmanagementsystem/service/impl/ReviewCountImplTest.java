package org.blank.projectmanagementsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.repository.ReviewCountRepository;
import org.blank.projectmanagementsystem.service.AmountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReviewCountImp.class})
@ExtendWith(SpringExtension.class)
class ReviewCountImplTest {
    @MockBean
    private AmountService amountService;

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private ReviewCountImp reviewCountImp;

    @MockBean
    private ReviewCountRepository reviewCountRepository;

    /**
     * Method under test: {@link ReviewCountImp#saveOrUpdate(ReviewDto)}
     */
    @Test
    void testSaveOrUpdate() {
        Client client = new Client();
        client.setEmail("jane.doe@example.org");
        client.setId(1L);
        client.setName("Name");
        client.setPhoneNumber("6625550144");
        client.setStatus(true);

        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");

        User projectManager = new User();
        projectManager.setActive(true);
        projectManager.setDefaultPassword(true);
        projectManager.setDepartment(department2);
        projectManager.setEmail("jane.doe@example.org");
        projectManager.setId(1L);
        projectManager.setName("Name");
        projectManager.setPassword("iloveyou");
        projectManager.setPhone("6625550144");
        projectManager.setRole(Role.PMO);
        projectManager.setUserRole("User Role");
        projectManager.setUsername("janedoe");

        Project project = new Project();
        project.setAmounts(new ArrayList<>());
        project.setArchitectures(new HashSet<>());
        project.setBackground("Background");
        project.setClient(client);
        project.setContractMembers(new HashSet<>());
        project.setDeliverables(new HashSet<>());
        project.setDepartment(department);
        project.setDuration("Duration");
        project.setEndDate(LocalDate.of(1970, 1, 1));
        project.setFocMembers(new HashSet<>());
        project.setId(1L);
        project.setName("Name");
        project.setObjective("Objective");
        project.setProjectManager(projectManager);
        project.setReviewCounts(new ArrayList<>());
        project.setStartDate(LocalDate.of(1970, 1, 1));
        project.setStatus(ProjectStatus.ONGOING);
        project.setSystemOutlines(new HashSet<>());

        ReviewCount reviewCount = new ReviewCount();
        reviewCount.setCount(3);
        reviewCount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        reviewCount.setId(1L);
        reviewCount.setProject(project);
        reviewCount.setReviewerType(ReviewerType.EXTERNAL);

        Client client2 = new Client();
        client2.setEmail("jane.doe@example.org");
        client2.setId(1L);
        client2.setName("Name");
        client2.setPhoneNumber("6625550144");
        client2.setStatus(true);

        Department department3 = new Department();
        department3.setActive(true);
        department3.setId(1);
        department3.setName("Name");

        Department department4 = new Department();
        department4.setActive(true);
        department4.setId(1);
        department4.setName("Name");

        User projectManager2 = new User();
        projectManager2.setActive(true);
        projectManager2.setDefaultPassword(true);
        projectManager2.setDepartment(department4);
        projectManager2.setEmail("jane.doe@example.org");
        projectManager2.setId(1L);
        projectManager2.setName("Name");
        projectManager2.setPassword("iloveyou");
        projectManager2.setPhone("6625550144");
        projectManager2.setRole(Role.PMO);
        projectManager2.setUserRole("User Role");
        projectManager2.setUsername("janedoe");

        Project project2 = new Project();
        project2.setAmounts(new ArrayList<>());
        project2.setArchitectures(new HashSet<>());
        project2.setBackground("Background");
        project2.setClient(client2);
        project2.setContractMembers(new HashSet<>());
        project2.setDeliverables(new HashSet<>());
        project2.setDepartment(department3);
        project2.setDuration("Duration");
        project2.setEndDate(LocalDate.of(1970, 1, 1));
        project2.setFocMembers(new HashSet<>());
        project2.setId(1L);
        project2.setName("Name");
        project2.setObjective("Objective");
        project2.setProjectManager(projectManager2);
        project2.setReviewCounts(new ArrayList<>());
        project2.setStartDate(LocalDate.of(1970, 1, 1));
        project2.setStatus(ProjectStatus.ONGOING);
        project2.setSystemOutlines(new HashSet<>());

        ReviewCount reviewCount2 = new ReviewCount();
        reviewCount2.setCount(3);
        reviewCount2.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        reviewCount2.setId(1L);
        reviewCount2.setProject(project2);
        reviewCount2.setReviewerType(ReviewerType.EXTERNAL);
        when(reviewCountRepository.save(Mockito.<ReviewCount>any())).thenReturn(reviewCount);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any())).thenReturn(reviewCount2);

        Client client3 = new Client();
        client3.setEmail("jane.doe@example.org");
        client3.setId(1L);
        client3.setName("Name");
        client3.setPhoneNumber("6625550144");
        client3.setStatus(true);

        Department department5 = new Department();
        department5.setActive(true);
        department5.setId(1);
        department5.setName("Name");

        Department department6 = new Department();
        department6.setActive(true);
        department6.setId(1);
        department6.setName("Name");

        User projectManager3 = new User();
        projectManager3.setActive(true);
        projectManager3.setDefaultPassword(true);
        projectManager3.setDepartment(department6);
        projectManager3.setEmail("jane.doe@example.org");
        projectManager3.setId(1L);
        projectManager3.setName("Name");
        projectManager3.setPassword("iloveyou");
        projectManager3.setPhone("6625550144");
        projectManager3.setRole(Role.PMO);
        projectManager3.setUserRole("User Role");
        projectManager3.setUsername("janedoe");

        Project project3 = new Project();
        project3.setAmounts(new ArrayList<>());
        project3.setArchitectures(new HashSet<>());
        project3.setBackground("Background");
        project3.setClient(client3);
        project3.setContractMembers(new HashSet<>());
        project3.setDeliverables(new HashSet<>());
        project3.setDepartment(department5);
        project3.setDuration("Duration");
        project3.setEndDate(LocalDate.of(1970, 1, 1));
        project3.setFocMembers(new HashSet<>());
        project3.setId(1L);
        project3.setName("Name");
        project3.setObjective("Objective");
        project3.setProjectManager(projectManager3);
        project3.setReviewCounts(new ArrayList<>());
        project3.setStartDate(LocalDate.of(1970, 1, 1));
        project3.setStatus(ProjectStatus.ONGOING);
        project3.setSystemOutlines(new HashSet<>());
        Optional<Project> ofResult = Optional.of(project3);
        when(projectRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ReviewDto reviewDto = ReviewDto.builder()
                .count(3)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .id(1L)
                .projectId(1L)
                .reviewerType(ReviewerType.EXTERNAL)
                .build();
        ReviewDto actualSaveOrUpdateResult = reviewCountImp.saveOrUpdate(reviewDto);
        verify(reviewCountRepository).findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any());
        verify(projectRepository).findById(Mockito.<Long>any());
        verify(reviewCountRepository).save(Mockito.<ReviewCount>any());
        assertEquals(1L, actualSaveOrUpdateResult.getId().longValue());
        assertEquals(1L, actualSaveOrUpdateResult.getProjectId().longValue());
        assertEquals(3, actualSaveOrUpdateResult.getCount().intValue());
        assertEquals(DevelopmentPhase.BASIC_DESIGN, actualSaveOrUpdateResult.getDevelopmentPhase());
        assertEquals(ReviewerType.EXTERNAL, actualSaveOrUpdateResult.getReviewerType());
    }

    /**
     * Method under test: {@link ReviewCountImp#getAllReviewCount()}
     */
    @Test
    void testGetAllReviewCount() {
        ArrayList<ReviewCount> reviewCountList = new ArrayList<>();
        when(reviewCountRepository.findAll()).thenReturn(reviewCountList);
        List<ReviewCount> actualAllReviewCount = reviewCountImp.getAllReviewCount();
        verify(reviewCountRepository).findAll();
        assertTrue(actualAllReviewCount.isEmpty());
        assertSame(reviewCountList, actualAllReviewCount);
    }

    /**
     * Method under test: {@link ReviewCountImp#findByProjectId(Long)}
     */
    @Test
    void testFindByProjectId() {
        ArrayList<ReviewCount> reviewCountList = new ArrayList<>();
        when(reviewCountRepository.findByProjectId(Mockito.<Long>any())).thenReturn(reviewCountList);
        List<ReviewCount> actualFindByProjectIdResult = reviewCountImp.findByProjectId(1L);
        verify(reviewCountRepository).findByProjectId(Mockito.<Long>any());
        assertTrue(actualFindByProjectIdResult.isEmpty());
        assertSame(reviewCountList, actualFindByProjectIdResult);
    }

    /**
     * Method under test:
     * {@link ReviewCountImp#calculateBasicDesignKpi(Long, DevelopmentPhase, ReviewerType)}
     */
    @Test
    void testCalculateBasicDesignKpi() {
        Client client = new Client();
        client.setEmail("jane.doe@example.org");
        client.setId(1L);
        client.setName("Name");
        client.setPhoneNumber("6625550144");
        client.setStatus(true);

        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");

        User projectManager = new User();
        projectManager.setActive(true);
        projectManager.setDefaultPassword(true);
        projectManager.setDepartment(department2);
        projectManager.setEmail("jane.doe@example.org");
        projectManager.setId(1L);
        projectManager.setName("Name");
        projectManager.setPassword("iloveyou");
        projectManager.setPhone("6625550144");
        projectManager.setRole(Role.PMO);
        projectManager.setUserRole("User Role");
        projectManager.setUsername("janedoe");

        Project project = new Project();
        project.setAmounts(new ArrayList<>());
        project.setArchitectures(new HashSet<>());
        project.setBackground("Background");
        project.setClient(client);
        project.setContractMembers(new HashSet<>());
        project.setDeliverables(new HashSet<>());
        project.setDepartment(department);
        project.setDuration("Duration");
        project.setEndDate(LocalDate.of(1970, 1, 1));
        project.setFocMembers(new HashSet<>());
        project.setId(1L);
        project.setName("Name");
        project.setObjective("Objective");
        project.setProjectManager(projectManager);
        project.setReviewCounts(new ArrayList<>());
        project.setStartDate(LocalDate.of(1970, 1, 1));
        project.setStatus(ProjectStatus.ONGOING);
        project.setSystemOutlines(new HashSet<>());

        ReviewCount reviewCount = new ReviewCount();
        reviewCount.setCount(3);
        reviewCount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        reviewCount.setId(1L);
        reviewCount.setProject(project);
        reviewCount.setReviewerType(ReviewerType.EXTERNAL);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any())).thenReturn(reviewCount);

        Client client2 = new Client();
        client2.setEmail("jane.doe@example.org");
        client2.setId(1L);
        client2.setName("Name");
        client2.setPhoneNumber("6625550144");
        client2.setStatus(true);

        Department department3 = new Department();
        department3.setActive(true);
        department3.setId(1);
        department3.setName("Name");

        Department department4 = new Department();
        department4.setActive(true);
        department4.setId(1);
        department4.setName("Name");

        User projectManager2 = new User();
        projectManager2.setActive(true);
        projectManager2.setDefaultPassword(true);
        projectManager2.setDepartment(department4);
        projectManager2.setEmail("jane.doe@example.org");
        projectManager2.setId(1L);
        projectManager2.setName("Name");
        projectManager2.setPassword("iloveyou");
        projectManager2.setPhone("6625550144");
        projectManager2.setRole(Role.PMO);
        projectManager2.setUserRole("User Role");
        projectManager2.setUsername("janedoe");

        Project project2 = new Project();
        project2.setAmounts(new ArrayList<>());
        project2.setArchitectures(new HashSet<>());
        project2.setBackground("Background");
        project2.setClient(client2);
        project2.setContractMembers(new HashSet<>());
        project2.setDeliverables(new HashSet<>());
        project2.setDepartment(department3);
        project2.setDuration("Duration");
        project2.setEndDate(LocalDate.of(1970, 1, 1));
        project2.setFocMembers(new HashSet<>());
        project2.setId(1L);
        project2.setName("Name");
        project2.setObjective("Objective");
        project2.setProjectManager(projectManager2);
        project2.setReviewCounts(new ArrayList<>());
        project2.setStartDate(LocalDate.of(1970, 1, 1));
        project2.setStatus(ProjectStatus.ONGOING);
        project2.setSystemOutlines(new HashSet<>());

        Amount amount = new Amount();
        amount.setAmount(10);
        amount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        amount.setId(1L);
        amount.setProject(project2);
        when(amountService.findByProjectIdAndDevelopmentPhase(Mockito.<Long>any(), Mockito.<DevelopmentPhase>any()))
                .thenReturn(amount);
        double actualCalculateBasicDesignKpiResult = reviewCountImp.calculateBasicDesignKpi(1L,
                DevelopmentPhase.BASIC_DESIGN, ReviewerType.EXTERNAL);
        verify(reviewCountRepository).findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any());
        verify(amountService).findByProjectIdAndDevelopmentPhase(Mockito.<Long>any(), Mockito.<DevelopmentPhase>any());
        assertEquals(3.3333333333333335d, actualCalculateBasicDesignKpiResult);
    }

    /**
     * Method under test:
     * {@link ReviewCountImp#calculateBasicDesignKpi(Long, DevelopmentPhase, ReviewerType)}
     */
    @Test
    void testCalculateBasicDesignKpi2() {
        Client client = new Client();
        client.setEmail("jane.doe@example.org");
        client.setId(1L);
        client.setName("Name");
        client.setPhoneNumber("6625550144");
        client.setStatus(true);

        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");

        User projectManager = new User();
        projectManager.setActive(true);
        projectManager.setDefaultPassword(true);
        projectManager.setDepartment(department2);
        projectManager.setEmail("jane.doe@example.org");
        projectManager.setId(1L);
        projectManager.setName("Name");
        projectManager.setPassword("iloveyou");
        projectManager.setPhone("6625550144");
        projectManager.setRole(Role.PMO);
        projectManager.setUserRole("User Role");
        projectManager.setUsername("janedoe");

        Project project = new Project();
        project.setAmounts(new ArrayList<>());
        project.setArchitectures(new HashSet<>());
        project.setBackground("Background");
        project.setClient(client);
        project.setContractMembers(new HashSet<>());
        project.setDeliverables(new HashSet<>());
        project.setDepartment(department);
        project.setDuration("Duration");
        project.setEndDate(LocalDate.of(1970, 1, 1));
        project.setFocMembers(new HashSet<>());
        project.setId(1L);
        project.setName("Name");
        project.setObjective("Objective");
        project.setProjectManager(projectManager);
        project.setReviewCounts(new ArrayList<>());
        project.setStartDate(LocalDate.of(1970, 1, 1));
        project.setStatus(ProjectStatus.ONGOING);
        project.setSystemOutlines(new HashSet<>());
        ReviewCount reviewCount = mock(ReviewCount.class);
        when(reviewCount.getCount()).thenReturn(3);
        doNothing().when(reviewCount).setCount(anyInt());
        doNothing().when(reviewCount).setDevelopmentPhase(Mockito.<DevelopmentPhase>any());
        doNothing().when(reviewCount).setId(Mockito.<Long>any());
        doNothing().when(reviewCount).setProject(Mockito.<Project>any());
        doNothing().when(reviewCount).setReviewerType(Mockito.<ReviewerType>any());
        reviewCount.setCount(3);
        reviewCount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        reviewCount.setId(1L);
        reviewCount.setProject(project);
        reviewCount.setReviewerType(ReviewerType.EXTERNAL);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any())).thenReturn(reviewCount);

        Client client2 = new Client();
        client2.setEmail("jane.doe@example.org");
        client2.setId(1L);
        client2.setName("Name");
        client2.setPhoneNumber("6625550144");
        client2.setStatus(true);

        Department department3 = new Department();
        department3.setActive(true);
        department3.setId(1);
        department3.setName("Name");

        Department department4 = new Department();
        department4.setActive(true);
        department4.setId(1);
        department4.setName("Name");

        User projectManager2 = new User();
        projectManager2.setActive(true);
        projectManager2.setDefaultPassword(true);
        projectManager2.setDepartment(department4);
        projectManager2.setEmail("jane.doe@example.org");
        projectManager2.setId(1L);
        projectManager2.setName("Name");
        projectManager2.setPassword("iloveyou");
        projectManager2.setPhone("6625550144");
        projectManager2.setRole(Role.PMO);
        projectManager2.setUserRole("User Role");
        projectManager2.setUsername("janedoe");

        Project project2 = new Project();
        project2.setAmounts(new ArrayList<>());
        project2.setArchitectures(new HashSet<>());
        project2.setBackground("Background");
        project2.setClient(client2);
        project2.setContractMembers(new HashSet<>());
        project2.setDeliverables(new HashSet<>());
        project2.setDepartment(department3);
        project2.setDuration("Duration");
        project2.setEndDate(LocalDate.of(1970, 1, 1));
        project2.setFocMembers(new HashSet<>());
        project2.setId(1L);
        project2.setName("Name");
        project2.setObjective("Objective");
        project2.setProjectManager(projectManager2);
        project2.setReviewCounts(new ArrayList<>());
        project2.setStartDate(LocalDate.of(1970, 1, 1));
        project2.setStatus(ProjectStatus.ONGOING);
        project2.setSystemOutlines(new HashSet<>());

        Amount amount = new Amount();
        amount.setAmount(10);
        amount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        amount.setId(1L);
        amount.setProject(project2);
        when(amountService.findByProjectIdAndDevelopmentPhase(Mockito.<Long>any(), Mockito.<DevelopmentPhase>any()))
                .thenReturn(amount);
        double actualCalculateBasicDesignKpiResult = reviewCountImp.calculateBasicDesignKpi(1L,
                DevelopmentPhase.BASIC_DESIGN, ReviewerType.EXTERNAL);
        verify(reviewCount, atLeast(1)).getCount();
        verify(reviewCount).setCount(anyInt());
        verify(reviewCount).setDevelopmentPhase(Mockito.<DevelopmentPhase>any());
        verify(reviewCount).setId(Mockito.<Long>any());
        verify(reviewCount).setProject(Mockito.<Project>any());
        verify(reviewCount).setReviewerType(Mockito.<ReviewerType>any());
        verify(reviewCountRepository).findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any());
        verify(amountService).findByProjectIdAndDevelopmentPhase(Mockito.<Long>any(), Mockito.<DevelopmentPhase>any());
        assertEquals(3.3333333333333335d, actualCalculateBasicDesignKpiResult);
    }

    /**
     * Method under test:
     * {@link ReviewCountImp#calculateBasicDesignKpi(Long, DevelopmentPhase, ReviewerType)}
     */
    @Test
    void testCalculateBasicDesignKpi3() {
        Client client = new Client();
        client.setEmail("jane.doe@example.org");
        client.setId(1L);
        client.setName("Name");
        client.setPhoneNumber("6625550144");
        client.setStatus(true);

        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");

        User projectManager = new User();
        projectManager.setActive(true);
        projectManager.setDefaultPassword(true);
        projectManager.setDepartment(department2);
        projectManager.setEmail("jane.doe@example.org");
        projectManager.setId(1L);
        projectManager.setName("Name");
        projectManager.setPassword("iloveyou");
        projectManager.setPhone("6625550144");
        projectManager.setRole(Role.PMO);
        projectManager.setUserRole("User Role");
        projectManager.setUsername("janedoe");

        Project project = new Project();
        project.setAmounts(new ArrayList<>());
        project.setArchitectures(new HashSet<>());
        project.setBackground("Background");
        project.setClient(client);
        project.setContractMembers(new HashSet<>());
        project.setDeliverables(new HashSet<>());
        project.setDepartment(department);
        project.setDuration("Duration");
        project.setEndDate(LocalDate.of(1970, 1, 1));
        project.setFocMembers(new HashSet<>());
        project.setId(1L);
        project.setName("Name");
        project.setObjective("Objective");
        project.setProjectManager(projectManager);
        project.setReviewCounts(new ArrayList<>());
        project.setStartDate(LocalDate.of(1970, 1, 1));
        project.setStatus(ProjectStatus.ONGOING);
        project.setSystemOutlines(new HashSet<>());
        ReviewCount reviewCount = mock(ReviewCount.class);
        when(reviewCount.getCount()).thenReturn(0);
        doNothing().when(reviewCount).setCount(anyInt());
        doNothing().when(reviewCount).setDevelopmentPhase(Mockito.<DevelopmentPhase>any());
        doNothing().when(reviewCount).setId(Mockito.<Long>any());
        doNothing().when(reviewCount).setProject(Mockito.<Project>any());
        doNothing().when(reviewCount).setReviewerType(Mockito.<ReviewerType>any());
        reviewCount.setCount(3);
        reviewCount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        reviewCount.setId(1L);
        reviewCount.setProject(project);
        reviewCount.setReviewerType(ReviewerType.EXTERNAL);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any())).thenReturn(reviewCount);
        double actualCalculateBasicDesignKpiResult = reviewCountImp.calculateBasicDesignKpi(1L,
                DevelopmentPhase.BASIC_DESIGN, ReviewerType.EXTERNAL);
        verify(reviewCount).getCount();
        verify(reviewCount).setCount(anyInt());
        verify(reviewCount).setDevelopmentPhase(Mockito.<DevelopmentPhase>any());
        verify(reviewCount).setId(Mockito.<Long>any());
        verify(reviewCount).setProject(Mockito.<Project>any());
        verify(reviewCount).setReviewerType(Mockito.<ReviewerType>any());
        verify(reviewCountRepository).findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any());
        assertEquals(-1.0d, actualCalculateBasicDesignKpiResult);
    }

    /**
     * Method under test:
     * {@link ReviewCountImp#findByProjectIdAndDevelopmentPhaseAndReviewerType(Long, DevelopmentPhase, ReviewerType)}
     */
    @Test
    void testFindByProjectIdAndDevelopmentPhaseAndReviewerType() {
        Client client = new Client();
        client.setEmail("jane.doe@example.org");
        client.setId(1L);
        client.setName("Name");
        client.setPhoneNumber("6625550144");
        client.setStatus(true);

        Department department = new Department();
        department.setActive(true);
        department.setId(1);
        department.setName("Name");

        Department department2 = new Department();
        department2.setActive(true);
        department2.setId(1);
        department2.setName("Name");

        User projectManager = new User();
        projectManager.setActive(true);
        projectManager.setDefaultPassword(true);
        projectManager.setDepartment(department2);
        projectManager.setEmail("jane.doe@example.org");
        projectManager.setId(1L);
        projectManager.setName("Name");
        projectManager.setPassword("iloveyou");
        projectManager.setPhone("6625550144");
        projectManager.setRole(Role.PMO);
        projectManager.setUserRole("User Role");
        projectManager.setUsername("janedoe");

        Project project = new Project();
        project.setAmounts(new ArrayList<>());
        project.setArchitectures(new HashSet<>());
        project.setBackground("Background");
        project.setClient(client);
        project.setContractMembers(new HashSet<>());
        project.setDeliverables(new HashSet<>());
        project.setDepartment(department);
        project.setDuration("Duration");
        project.setEndDate(LocalDate.of(1970, 1, 1));
        project.setFocMembers(new HashSet<>());
        project.setId(1L);
        project.setName("Name");
        project.setObjective("Objective");
        project.setProjectManager(projectManager);
        project.setReviewCounts(new ArrayList<>());
        project.setStartDate(LocalDate.of(1970, 1, 1));
        project.setStatus(ProjectStatus.ONGOING);
        project.setSystemOutlines(new HashSet<>());

        ReviewCount reviewCount = new ReviewCount();
        reviewCount.setCount(3);
        reviewCount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        reviewCount.setId(1L);
        reviewCount.setProject(project);
        reviewCount.setReviewerType(ReviewerType.EXTERNAL);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any())).thenReturn(reviewCount);
        ReviewCount actualFindByProjectIdAndDevelopmentPhaseAndReviewerTypeResult = reviewCountImp
                .findByProjectIdAndDevelopmentPhaseAndReviewerType(1L, DevelopmentPhase.BASIC_DESIGN, ReviewerType.EXTERNAL);
        verify(reviewCountRepository).findByProjectIdAndDevelopmentPhaseAndReviewerType(Mockito.<Long>any(),
                Mockito.<DevelopmentPhase>any(), Mockito.<ReviewerType>any());
        assertSame(reviewCount, actualFindByProjectIdAndDevelopmentPhaseAndReviewerTypeResult);
    }

}
