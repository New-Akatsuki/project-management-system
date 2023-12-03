package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.*;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.dto.PhaseDto;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TaskAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TaskAPITest {
    @Autowired
    private TaskAPI taskAPI;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @MockBean
    private PhaseService phaseService;
    @MockBean
    private ProjectService projectService;
    @Autowired
    private ObjectMapper objectMapper;

    private PhaseDto phaseDto;
    private TaskViewObject taskViewObject;
    private Project project;
    private TaskFormInput taskFormInput;

    private Phase phase;
    private Task updatedTask;

    @BeforeEach
    void setUp() {

        project = Project.builder()
                .id(1L)
                .name("Project 1")
                .build();


        phaseDto = PhaseDto.builder()
                .id(1L)
                .name("Phase 1")
                .projectId(1L)
                .build();
        taskViewObject = TaskViewObject.builder()
                .id(1L)
                .name("Task 1")
                .phase(1L)
                .duration(1)
                .start_date(LocalDate.parse("2021-01-01"))
                .end_date(LocalDate.parse("2021-02-01"))
                .actual_due_date(LocalDate.parse("2021-02-01"))
                .plan_hours(50F)
                .actual_hours(55F)
                .build();
        taskFormInput = TaskFormInput.builder()
                .id(1L)
                .name("Task 1")
                .phase(1L)
                .duration(1)
                .start_date(LocalDate.parse("2021-01-01"))
                .end_date(LocalDate.parse("2021-02-01"))
                .actual_due_date(LocalDate.parse("2021-02-01"))
                .plan_hours(50F)
                .actual_hours(55F)
                .build();

         phase = Phase.builder()
                .id(1L)
                .name("Phase 1")
                .project(project)
                .build();

         updatedTask = Task.builder()
                .id(1L)
                .name("Updated Task")
                .description("Updated Task Description")
                .phase(phase)
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.parse("2021-01-02"))
                .dueDate(LocalDate.parse("2021-02-02"))
                .planHours(60F)
                .actualDueDate(LocalDate.parse("2021-02-02"))
                .actualHours(65F)
                .status(true)
                .group(TaskGroup.A)
                .type(TaskType.TASK)
                .build();


    }

    @Test
    void testGetPhaseData() throws Exception {
        when(taskService.getTasksByProject(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(phaseService.getPhases(anyLong())).thenReturn(new ArrayList<>());

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
        when(projectService.getProjectMembers(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(projectService.getProject(anyLong())).thenReturn(project);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/get-all-project-data");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("projectId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(taskAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<Map><endDate>1970</endDate><endDate>1</endDate><endDate>1</endDate><startDate>1970</startDate><startDate"
                                        + ">1</startDate><startDate>1</startDate></Map>"));
    }



    @Test
    public void taskApi_addPhaseIsOk() throws Exception{
        given(phaseService.createPhase(phaseDto)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-phase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(phaseDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Phase 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId", CoreMatchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void taskApi_updatePhaseIsOk() throws Exception{
        given(phaseService.updatePhase(phaseDto)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/update-phase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(phaseDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Phase 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId", CoreMatchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void taskApi_isDeletePhaseOk() throws Exception{

        doNothing().when(phaseService).deletePhase(1L);
        ResultActions response = mockMvc.perform(delete("/delete-phase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(phaseDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Phase 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId", CoreMatchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void taskApi_getTasksByProjectIdIsOk() throws Exception{
        when(taskService.getMemberTaskByProject(1L)).thenReturn(List.of(taskViewObject));
        ResultActions response = mockMvc.perform(get("/get-tasks/{projectId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("projectId", "1")
                .param("name", "Task 1")
                .param("phase", "1")
                .param("duration", "1")
                .param("start_date", "2021-01-01")
                .param("end_date", "2021-02-01")
                .param("actual_due_date", "2021-02-01")
                .param("plan_hours", "50.0")
                .param("actual_hours", "55.0"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phase", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].duration", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].start_date", CoreMatchers.is("2021-01-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].end_date", CoreMatchers.is("2021-02-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].actual_due_date", CoreMatchers.is("2021-02-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].plan_hours", CoreMatchers.is(50.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].actual_hours", CoreMatchers.is(55.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void taskApi_addTaskIsOk() throws Exception {
        when(taskService.createTask(taskFormInput)).thenReturn(taskViewObject);

        ResultActions response = mockMvc.perform(post("/add-task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskFormInput)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phase", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.start_date", CoreMatchers.is("2021-01-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.end_date", CoreMatchers.is("2021-02-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actual_due_date", CoreMatchers.is("2021-02-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.plan_hours", CoreMatchers.is(50.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actual_hours", CoreMatchers.is(55.0)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void taskApi_updateTaskIsOk() throws Exception {

        when(taskService.updateTask(any(TaskFormInput.class))).thenReturn(updatedTask);

        ResultActions response = mockMvc.perform(put("/update-task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskFormInput)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Updated Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phase", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is("Updated Task Description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority", CoreMatchers.is("medium")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.start_date", CoreMatchers.is("2021-01-02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.end_date", CoreMatchers.is("2021-02-02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.plan_hours", CoreMatchers.is(60.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actual_due_date", CoreMatchers.is("2021-02-02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actual_hours", CoreMatchers.is(65.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group", CoreMatchers.is("A")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is("task")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void taskApi_deleteTaskIsOk() throws Exception{
        doNothing().when(taskService).deleteTask(1L);
        ResultActions response = mockMvc.perform(delete("/delete-task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskFormInput)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phase", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.start_date", CoreMatchers.is("2021-01-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.end_date", CoreMatchers.is("2021-02-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actual_due_date", CoreMatchers.is("2021-02-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.plan_hours", CoreMatchers.is(50.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actual_hours", CoreMatchers.is(55.0)))
                .andDo(MockMvcResultHandlers.print());
    }
}