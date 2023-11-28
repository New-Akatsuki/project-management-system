package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.blank.projectmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@WebMvcTest(controllers = ProjectAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProjectAPITest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjectService projectService;
    @MockBean
    private ReportService reportService;
    @MockBean
    private UserService userService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private SystemOutlineRepository systemOutlineRepository;
    @MockBean
    private ArchitectureRepository architectureRepository;
    @MockBean
    private DeliverableRepository deliverableRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private ProjectViewObject projectViewObject;

    private Project project;
    private User user;

    private Department department;

    private Client client;

    private Architecture architecture;
    private SystemOutline systemOutline;
    private Deliverable deliverable;
    private Amount amount;
    private ReviewCount reviewCount;
    private ProjectFormInput projectFormInput;

    @BeforeEach
    void setUp() {
        amount = Amount.builder()
                .id(1L)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .amount(1000)
                .build();

        reviewCount = ReviewCount.builder()
                .id(1L)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .count(3)
                .reviewerType(ReviewerType.INTERNAL)
                .build();


        deliverable = Deliverable.builder()
                .id(1L)
                .name("Deliverable 1")
                .build();

        systemOutline = SystemOutline.builder()
                .id(1L)
                .name("System Outline 1")
                .build();


        architecture = Architecture.builder()
                .id(1L)
                .name("Architecture 1")
                .type(ArchitectureType.API)
                .build();
        department = Department.builder()
                .id(1)
                .name("Department 1")
                .build();

        user = User.builder()
                .id(1L)
                .username("user1")
                .password("password")
                .role(Role.PM)

                .id(2L)
                .username("user2")
                .password("password")
                .role(Role.MEMBER)
                .build();



        client = Client.builder()
                .id(1L)
                .name("Client 1")
                .build();

        project = Project.builder()
                .id(1L)
                .name("Project 1")
                .background("Background 1")
                .objective("Objective 1")
                .duration("Duration 1")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2021, 2, 1))
                .client(client)
                .projectManager(user)
                .department(department)
                .contractMembers(Set.of(user))
                .focMembers(Set.of(user))
                .architectures(Set.of(architecture))
                .systemOutlines(Set.of(systemOutline))
                .deliverables(Set.of(deliverable))
                .amounts(List.of(amount))
                .reviewCounts(List.of(reviewCount))
                .build();

        projectFormInput = ProjectFormInput.builder()
                .name("Project 1")
                .background("Background 1")
                .objective("Objective 1")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2021, 2, 1))
                .build();

        projectViewObject = ProjectViewObject.builder()
                .id(1L)
                .name("Project 1")
                .background("Background 1")
                .objective("Objective 1")
                .duration("Duration 1")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2021, 2, 1))
                .build();

    }
    @Test
    public void projectApi_getProjectIsOk() throws Exception{
//        when(projectService.getAllProjects()).thenReturn(List.of(projectViewObject));

        ResultActions response = mockMvc.perform(get("/get-projects")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(projectViewObject)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void projectApi_createProjectIsOK() throws Exception{
        given(projectService.saveProject(projectFormInput)).willReturn(project);
        ResultActions response = mockMvc.perform(post("/add-project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectFormInput)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void projectApi_getProjectByIdIsOk() throws Exception{
        when(projectService.getProjectById(1L)).thenReturn(projectViewObject);

        ResultActions response = mockMvc.perform(get("/get-projectDetails-byId/{id}",1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void projectApi_exportProjectPdfIsOk() throws Exception{
        when(projectService.getProject(1L)).thenReturn(project);
        byte[] mockPdfBytes = new byte[2048];
        when(reportService.generatePdf(Mockito.anyMap(), Mockito.anyString())).thenReturn(mockPdfBytes);

        ResultActions response = mockMvc.perform(get("/export-project-pdf")
                .contentType(MediaType.APPLICATION_JSON)
                .param("projectId", "1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(mockPdfBytes))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void projectApi_exportProjectXlsxIsOk() throws Exception{
        when(projectService.getProject(1L)).thenReturn(project);
        byte[] mockExcelBytes = new byte[2048];
        when(reportService.generateExcel(Mockito.anyMap(), Mockito.anyString())).thenReturn(mockExcelBytes);

        ResultActions response = mockMvc.perform(get("/export-project-xlsx")
                .contentType(MediaType.APPLICATION_JSON)
                .param("projectId", "1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(mockExcelBytes))
                .andDo(MockMvcResultHandlers.print());
    }

    private String buildHtmlRequestJson(String html) throws JsonProcessingException {
        return objectMapper.writeValueAsString(Map.of("html", html));
    }
}