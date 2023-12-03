package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.controller.ui.ProjectController;
import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectEditViewObject;
import org.blank.projectmanagementsystem.dto.ProjectReportDto;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.blank.projectmanagementsystem.service.impl.ProjectServiceImpl;
import org.blank.projectmanagementsystem.service.impl.ReportServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {ProjectAPI.class})
@ExtendWith(SpringExtension.class)
class ProjectAPITest {
    @Autowired
    private ProjectAPI projectAPI;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ReportService reportService;

    private MockMvc mockMvc;

    /**
     * Method under test: {@link ProjectAPI#createProject(ProjectFormInput)}
     */
    @Test
    void testCreateProject() {
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
        ProjectServiceImpl projectService = mock(ProjectServiceImpl.class);
        when(projectService.saveProject(Mockito.<ProjectFormInput>any())).thenReturn(project);
        ProjectAPI projectAPI = new ProjectAPI(projectService, new ReportServiceImpl());
        ResponseEntity<Project> actualCreateProjectResult = projectAPI.createProject(new ProjectFormInput());
        verify(projectService).saveProject(Mockito.<ProjectFormInput>any());
        assertEquals(200, actualCreateProjectResult.getStatusCodeValue());
        assertTrue(actualCreateProjectResult.hasBody());
        assertTrue(actualCreateProjectResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProjectAPI#export(Long)}
     */
    @Test
    void testExport() throws Exception {
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
        when(projectService.getProject(anyLong())).thenReturn(project);
        when(reportService.generatePdf(Mockito.<Map<String, Object>>any(), Mockito.<String>any()))
                .thenReturn("AXAXAXAX".getBytes("UTF-8"));
        MockHttpServletRequestBuilder getResult = get("/export-project-pdf");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("projectId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(projectAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/pdf"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

    /**
     * Method under test: {@link ProjectAPI#exportXlsx(Long)}
     */
    @Test
    void testExportXlsx() throws Exception {
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
        when(projectService.getProject(anyLong())).thenReturn(project);
        when(reportService.generateExcel(Mockito.<Map<String, Object>>any(), Mockito.<String>any()))
                .thenReturn("AXAXAXAX".getBytes("UTF-8"));
        MockHttpServletRequestBuilder getResult = get("/export-project-xlsx");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("projectId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(projectAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/octet-stream"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

    /**
     * Method under test:  {@link ProjectAPI#getProject()}
     */
    @Test
    void testGetProject() throws Exception {
        when(projectService.getAllProjects()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = get("/get-projects");
        MockMvcBuilders.standaloneSetup(projectAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test:  {@link ProjectAPI#getProject()}
     */
    @Test
    void testGetProject2() throws Exception {
        when(projectService.getAllProjects()).thenReturn(new ArrayList<>());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(projectAPI).build().perform(requestBuilder);
        actualPerformResult.andExpect(status().isNotFound());
    }

    /**
     * Method under test:  {@link ProjectAPI#getProjectById(Long)}
     */
    @Test
    void testGetProjectById() throws Exception {
        when(projectService.getProjectByID(Mockito.<Long>any())).thenReturn(new ProjectEditViewObject());
        MockHttpServletRequestBuilder requestBuilder = get("/get-projectDetails-byId/{id}", 1L);
        MockMvcBuilders.standaloneSetup(projectAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<ProjectEditViewObject><name/><clientId/><departmentId/><startDate/><endDate/><objective/><background"
                                        + "/></ProjectEditViewObject>"));
    }

    /**
     * Method under test:  {@link ProjectAPI#toggleStatus(Long)}
     */
    @Test
    void testToggleStatus() throws Exception {
        when(projectService.toggleProjectStatus(Mockito.<Long>any())).thenReturn("Toggle Project Status");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/toggle-project-status/{id}", 1L);
        MockMvcBuilders.standaloneSetup(projectAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Toggle Project Status"));
    }

    @Test
    public void testExportProjectPdfException() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectAPI).build();
        // Create sample data for testing
        Long projectId = 1L;

        // Mock the behavior of the service methods
        when(projectService.getProject(projectId)).thenReturn(new Project());

        // Mock the behavior of the reportService to throw an exception
        when(reportService.generatePdf(any(), any())).thenThrow(new RuntimeException("Mocked exception"));

        // Perform the GET request to export project PDF
        mockMvc.perform(get("/export-project-pdf")
                        .param("projectId", String.valueOf(projectId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testExportProjectXlsxException() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectAPI).build();
        // Create sample data for testing
        Long projectId = 1L;

        // Mock the behavior of the service methods
        when(projectService.getProject(projectId)).thenReturn(new Project());

        // Mock the behavior of the reportService to throw an exception
        when(reportService.generateExcel(any(), any())).thenThrow(new RuntimeException("Mocked exception"));

        // Perform the GET request to export project XLSX
        mockMvc.perform(get("/export-project-xlsx")
                        .param("projectId", String.valueOf(projectId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}