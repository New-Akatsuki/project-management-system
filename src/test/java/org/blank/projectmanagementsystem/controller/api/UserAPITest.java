package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.UpdateUserFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private SessionRegistry sessionRegistry;
    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserAPI userAPI;
    private UserViewObject userViewObject;
    private User user;
    private AddUserFormInput addUserFormInput;

    private ChangePasswordFormInput changePasswordFormInput;

    private Department department;
    private UpdateUserFormInput updateUserFormInput;

    private SessionInformation sessionInformation;

    @BeforeEach
    public void init() {
        department = Department.builder()
                .id(1)
                .name("Department 1")
                .active(true)
                .build();
        user = User.builder()
                .id(1L)
                .name("user1")
                .email("yephoneaung33002@gmail.com")
                .password("123456")
                .department(department)
                .role(Role.PMO)
                .active(true)
                .build();
        userViewObject = UserViewObject.builder()
                .id(1L)
                .name("user1")
                .email("yephoneaung33002@gmail.com")
                .departmentId(department.getId())
                .department("Department 1 ")
                .role("PMO")
                .active(true)
                .build();
        addUserFormInput = AddUserFormInput.builder()
                .name("user1")
                .email("yephoneaung33002@gmail.com")
                .department(Long.valueOf(department.getId()))
                .role("PMO")
                .build();

        changePasswordFormInput = ChangePasswordFormInput.builder()
                .currentPassword("123456")
                .newPassword("1234567")
                .confirmPassword("1234567")
                .build();

        updateUserFormInput = UpdateUserFormInput.builder()
                .id(1L)
                .name("user1")
                .email("yephoneaung33002@gmail.com")
                .department((department.getId()))
                .Role("PMO")
                .build();

        sessionInformation = mock(SessionInformation.class);


    }

    @Test
   public void userApi_getUserList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userViewObject));
        ResultActions response = mockMvc.perform(get("/get-users")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "user1")
                .param("email", "yephoneaung33002@gmail.com")
                .param("role", "PMO")
                .param("department", "1")
                .param("active", "true"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("user1")))
                .andExpect(jsonPath("$[0].email", CoreMatchers.is("yephoneaung33002@gmail.com")))
                .andExpect(jsonPath("$[0].departmentId", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].department", CoreMatchers.is("Department 1 ")))
                .andExpect(jsonPath("$[0].active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    void generateDefaultPasswordShouldReturnValidString() {
        String generatedPassword = userAPI.generateDefaultPassword();
        int passwordAsInt = Integer.parseInt(generatedPassword);
        assertTrue(passwordAsInt >= 0 && passwordAsInt <= 999999);
    }



    @Test
    public void userApi_addUser() throws Exception {
        given(userService.createMember(any(AddUserFormInput.class), any(String.class)))
                .willReturn(user);

        ResultActions response = mockMvc.perform(post("/member-create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addUserFormInput)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", CoreMatchers.is("user1")))
                .andExpect(jsonPath("$.email", CoreMatchers.is("yephoneaung33002@gmail.com")))
                .andExpect(jsonPath("$.department", CoreMatchers.is("Department 1")))
                .andExpect(jsonPath("$.active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void userApi_updateUserStatus() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        given(userService.save(user)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/member/status/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("newStatus", "true"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("user1")))
                .andExpect(jsonPath("$.email", CoreMatchers.is("yephoneaung33002@gmail.com")))
                .andExpect(jsonPath("$.active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void userApi_updateUser_Success() throws Exception {
        // Setup
        UpdateUserFormInput input = new UpdateUserFormInput(1L, "NewName", "newemail@example.com", 2, "PMO");
        User existingUser = User.builder().id(1L).build();
        Department department = Department.builder().id(2).name("NewDepartment").build();
        User updatedUser = User.builder().id(1L).name("NewName").email("newemail@example.com").department(department).role(Role.PMO).build();

        given(userService.getUserById(1L)).willReturn(existingUser);
        given(departmentService.getDepartmentById(2)).willReturn(department);
        given(userService.save(any(User.class))).willReturn(updatedUser);

        // Test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/user-edit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        // Assertions
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("NewName"))
                .andExpect(jsonPath("$.email").value("newemail@example.com"))
                .andExpect(jsonPath("$.department").value("NewDepartment"))
                .andExpect(jsonPath("$.role").value("PMO"));
    }

    @Test
    public void userApi_updateUser_UserNotFound() throws Exception {
        // Setup
        UpdateUserFormInput input = new UpdateUserFormInput(1L, "NewName", "newemail@example.com", 2, "PMO");

        given(userService.getUserById(1L)).willReturn(null);

        // Test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/user-edit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        // Assertions
        result.andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void userApi_getDepartmentList() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(List.of(department));
        ResultActions response = mockMvc.perform(get("/get-department")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Department 1")
                .param("active", "true"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Department 1")))
                .andExpect(jsonPath("$[0].active", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void checkCurrentPassword_Success() throws Exception {
        // Setup
        ChangePasswordFormInput input = new ChangePasswordFormInput("currentPassword", "newPassword", "newPassword");
        given(userService.checkCurrentPassword("currentPassword")).willReturn(true);

        // Test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users/check-current-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        result.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void userApi_checkUserByUserNameOrEmailExists() throws Exception {
        when(userService.checkUserExistOrNotWithUsername("user1")).thenReturn(true);
        when(userService.checkUserExistOrNotWithUsername("yephoneaung33002@gmail.com")).thenReturn(true);
        ResultActions response = mockMvc.perform(post("/check-user-by-username")
                .contentType(MediaType.APPLICATION_JSON)
                .param("data", "user1"));

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}