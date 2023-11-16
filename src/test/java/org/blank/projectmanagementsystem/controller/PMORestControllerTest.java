package org.blank.projectmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PMORestController.class)
public class PMORestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void testUpdateDepartmentWhenValidDepartmentThenSuccess() throws Exception {
        Department existingDepartment = new Department(1, "Existing Department", true);
        Department updatedDepartment = new Department(1, "Updated Department", true);

        given(departmentService.getDepartmentById(1)).willReturn(existingDepartment);
        given(departmentService.save(updatedDepartment)).willReturn(updatedDepartment);

        mockMvc.perform(MockMvcRequestBuilders.put("/department-edit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedDepartment)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(updatedDepartment)));
    }

    @Test
    public void testUpdateDepartmentWhenDepartmentNotFoundThenFailure() throws Exception {
        Department updatedDepartment = new Department(1, "Updated Department", true);

        given(departmentService.getDepartmentById(1)).willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/department-edit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedDepartment)))
                .andExpect(status().isNotFound());
    }
}