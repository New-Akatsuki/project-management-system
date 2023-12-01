//package org.blank.projectmanagementsystem.controller.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.blank.projectmanagementsystem.domain.entity.Project;
//import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
//import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
//import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
//import org.blank.projectmanagementsystem.service.ProjectService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.stubbing.Answer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@WebMvcTest(ProjectAPI.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ExtendWith(MockitoExtension.class)
//class ProjectAPITest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private ProjectService projectService;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private ProjectViewObject projectViewObject;
//
//    private Project project;
//
//    private ProjectFormInput projectFormInput;
//
//    @BeforeEach
//    void setUp() {
//        project = Project.builder()
//                .id(1L)
//                .name("Project 1")
//                .background("Background 1")
//                .objective("Objective 1")
//                .duration("Duration 1")
//                .startDate(LocalDate.of(2021, 1, 1))
//                .endDate(LocalDate.of(2021, 2, 1))
//                .build();
//
//        projectFormInput = ProjectFormInput.builder()
//                .name("Project 1")
//                .background("Background 1")
//                .objective("Objective 1")
//                .startDate(LocalDate.of(2021, 1, 1))
//                .endDate(LocalDate.of(2021, 2, 1))
//                .build();
//
//        projectViewObject = ProjectViewObject.builder()
//                .id(1L)
//                .name("Project 1")
//                .background("Background 1")
//                .objective("Objective 1")
//                .duration("Duration 1")
//                .startDate(LocalDate.of(2021, 1, 1))
//                .endDate(LocalDate.of(2021, 2, 1))
//                .build();
//
//    }
//    @Test
//    public void projectApi_getProjectIsOk() throws Exception{
////        when(projectService.getAllProjects()).thenReturn(List.of(projectViewObject));
//
//        ResultActions response = mockMvc.perform(get("/get-projects")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(projectViewObject)));
//
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    public void projectApi_createProjectIsOK() throws Exception{
//        given(projectService.saveProject(projectFormInput)).willReturn(project);
//        ResultActions response = mockMvc.perform(post("/add-project")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(projectFormInput)));
//
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//
//    @Test
//    public void projectApi_getProjectByIdIsOk() throws Exception{
//        when(projectService.getProjectByID(1L)).thenReturn(project);
//
//        ResultActions response = mockMvc.perform(get("/get-projectDetails-byId/{id}",1L)
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(project)));
//
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//}