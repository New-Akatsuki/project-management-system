package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.blank.projectmanagementsystem.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = DepartmentAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DepartmentAPITest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;
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
        ResultActions response = mockMvc.perform(get("/departments")
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
    public void departmentApi_addDepartment() throws Exception {
        given(departmentService.save(department)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/department-create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Department 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void departmentApi_updateDepartment() throws Exception {
        given(departmentService.getDepartmentById(department.getId())).willReturn(department);
        given(departmentService.save(department)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/department-edit/{id}", department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Department 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void departmentApi_toggleDepartmentStatus() throws Exception {
        given(departmentService.getDepartmentById(department.getId())).willReturn(department);
        given(departmentService.save(department)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/department-toggle/{departmentId}", department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Department status changed successfully"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void departmentApi_updateMemberStatus() throws Exception {
        when(departmentService.getDepartmentById(1)).thenReturn(department);
        given(departmentService.save(department)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/department/status/{id}", department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("newStatus", "false"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Department 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active", CoreMatchers.is(false)))
                .andDo(MockMvcResultHandlers.print());
    }
}