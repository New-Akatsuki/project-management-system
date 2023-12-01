package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ManMonthAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ManMonthAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;
    @MockBean
    private ProjectService projectService;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Department department;


    @BeforeEach
    public void init() {
        department = Department.builder()
                .id(1)
                .name("Department 1")
                .active(true)
                .build();

    }

    @Test
    public void manMonthApi_getDepartment() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(List.of(department));
        ResultActions response = mockMvc.perform(get("/api/get-department-data")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Department 1")
                .param("active", "true"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is("Department 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void manMonthApi_getProjectsByDepartment() throws Exception {
            given(projectService.getProjectsByDepartment(1)).willReturn(List.of(ProjectViewObject.builder()
                    .id(1L)
                    .name("Project 1")
                    .startDate(LocalDate.of(2021, 1, 1))
                    .endDate(LocalDate.of(2021, 3, 1))
                    .duration("2")
                    .build()));
            ResultActions response = mockMvc.perform(get("/api/get-projects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("id", "1")
                    .param("name", "Project 1")
                    .param("departmentId", "1")
                    .param("startDate", "2021-01-01")
                    .param("endDate", "2021-03-01")
                    .param("duration", "2"));
            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is("Project 1")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate", CoreMatchers.is("2021-01-01")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].endDate", CoreMatchers.is("2021-03-01")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].duration", CoreMatchers.is("2")))
                    .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void manMonthApi_getTaskByProject() throws Exception {
        given(taskService.getTasksByProject(1L)).willReturn(List.of(TaskViewObject.builder()
                .id(1L)
                .name("Task 1")
                .start_date(LocalDate.of(2021, 1, 1))
                .end_date(LocalDate.of(2021, 3, 1))
                .duration(2)
                .build()));
        ResultActions response = mockMvc.perform(get("/api/get-tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Task 1")
                .param("projectId", "1")
                .param("startDate", "2021-01-01")
                .param("endDate", "2021-03-01")
                .param("duration", "2"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].start_date", CoreMatchers.is("2021-01-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].end_date", CoreMatchers.is("2021-03-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].duration", CoreMatchers.is(2)))
                .andDo(MockMvcResultHandlers.print());
    }
}