package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.service.DepartmentService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DepartmentAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DepartmentAPITest {
    @Autowired
    private MockMvc mockMvc;
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
    public void departmentApi_getDepartments() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(List.of(department));
        ResultActions resposne = mockMvc.perform(get("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Department 1")
                .param("active", "true"));

        resposne.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Department 1")))
                .andExpect(jsonPath("$[0].active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void departmentApi_addDepartment() throws Exception {
        given(departmentService.save(department)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/department-create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Department 1")))
                .andExpect(jsonPath("$.active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void departmentApi_updateDepartment() throws Exception {
        given(departmentService.getDepartmentById(department.getId())).willReturn(department);
        given(departmentService.save(department)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/department-edit/{id}", department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Department 1")))
                .andExpect(jsonPath("$.active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testUpdateDepartmentNotFound() throws Exception {
        // Set up for a scenario where the department is not found
        Integer nonExistingId = 2;
        Department updatedDepartmentRequest = new Department(nonExistingId, "Updated Department", true);
        when(departmentService.getDepartmentById(Math.toIntExact(nonExistingId))).thenReturn(null);

        // Perform the PUT request to update department for a non-existing department
        ResultActions resultActions = mockMvc.perform(put("/department-edit/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDepartmentRequest)));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void departmentApi_toggleDepartmentStatus() throws Exception {
        given(departmentService.getDepartmentById(department.getId())).willReturn(department);
        given(departmentService.save(department)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/department-toggle/{departmentId}", department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));

        response.andExpect(status().isOk())
                .andExpect(content().string("Department status changed successfully"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testToggleDepartmentStatusNotFound() throws Exception {
        // Set up for a scenario where the department is not found
        int nonExistingId = 2;
        when(departmentService.getDepartmentById(Math.toIntExact(nonExistingId))).thenReturn(null);

        // Perform the PUT request to toggle department status for a non-existing department
        ResultActions resultActions = mockMvc.perform(put("/department-toggle/{departmentId}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateMemberStatus() throws Exception {
        // Create sample data for testing
        Integer departmentId = 1;
        Department department = new Department(departmentId, "Department", true);
        boolean newStatus = false;

        // Mock the behavior of the service methods
        when(departmentService.getDepartmentById(departmentId)).thenReturn(department);
        when(departmentService.save(department)).thenReturn(department);

        // Perform the PUT request to update member status
        ResultActions resultActions = mockMvc.perform(put("/department/status/{id}", departmentId)
                .param("newStatus", String.valueOf(newStatus))
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.active").value(false));

        // Verify that the service methods were called with the correct arguments
        Mockito.verify(departmentService).getDepartmentById(departmentId);
        Mockito.verify(departmentService).save(department);
    }

    @Test
    public void testUpdateMemberStatusNotFound() throws Exception {
        // Set up for a scenario where the department is not found
        Integer nonExistingId = 2;
        boolean newStatus = false;
        when(departmentService.getDepartmentById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update member status for a non-existing department
        ResultActions resultActions = mockMvc.perform(put("/department/status/{id}", nonExistingId)
                .param("newStatus", String.valueOf(newStatus))
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }
}