package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.IssueFormInput;
import org.blank.projectmanagementsystem.domain.formInput.IssueSolveFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.IssueSolutionViewObject;
import org.blank.projectmanagementsystem.service.IssueService;
import org.blank.projectmanagementsystem.service.ProjectService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = IssueAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class IssueAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IssueService issueService;
    @MockBean
    private ProjectService projectService;
    @Autowired
    private ObjectMapper objectMapper;

    private IssueCategory issueCategory;

    private IssuePlace issuePlace;

    private ResponsibleParty responsibleParty;

    private User user;

    private Project project;

    private IssueFormInput issueFormInput;

    private Issue issue;

    private AllIssueDisplayViewObject allIssueDisplayViewObject;

    private IssueDetailsViewObject issueDetailsViewObject;

    private IssueSolutionViewObject issueSolutionViewObject;

    private IssueSolveFormInput issueSolveFormInput;


    @BeforeEach
    public void init() {

        project = Project.builder()
                .id(1L)
                .name("Project 1")
                .build();
        user = User.builder()
                .id(1L)
                .name("yephoneaung33002@gmail.com")
                .username("user1")
                .role(Role.PM)
                .build();

        issueCategory = IssueCategory.builder()
                .id(1L)
                .name("Issue Category 1")
                .build();

        issuePlace = IssuePlace.builder()
                .id(1L)
                .name("Issue Place 1")
                .build();

        responsibleParty = ResponsibleParty.builder()
                .id(1L)
                .name("Responsible Party 1")
                .build();


        issueFormInput = IssueFormInput.builder()
                .id(1L)
                .title("Issue 1")
                .content("Issue 1")
                .directCause("Issue 1")
                .rootCause("Issue 1")
                .responsibleParty(1L)
                .issuePlace(1L)
                .issueCategory(1L)
                .build();

        issue = Issue.builder()
                .id(1L)
                .title("Issue 1")
                .content("Issue 1")
                .directCause("Issue 1")
                .rootCause("Issue 1")
                .responsibleParty(responsibleParty)
                .issuePlace(issuePlace)
                .issueCategory(issueCategory)
                .build();

        allIssueDisplayViewObject = AllIssueDisplayViewObject.builder()
                .id(1L)
                .title("Issue 1")
                .category("Issue Category 1")
                .place("Issue Place 1")
                .responsibleParty("Responsible Party 1")
                .solved(false)
                .build();

        issueDetailsViewObject = IssueDetailsViewObject.builder()
                .id(1L)
                .title("Issue 1")
                .category("Issue Category 1")
                .categoryId(1L)
                .place("Issue Place 1")
                .placeId(1L)
                .responsibleParty("Responsible Party 1")
                .responsiblePartyId(1L)
                .solved(false)
                .build();

        issueSolveFormInput = IssueSolveFormInput.builder()
                .id(1L)
                .coAction("Zero 1")
                .preAction("Pre 1")
                .impact("Impact 1")
                .build();


    }
    @Test
    public void issueApi_getIssueCategoryIsOk() throws Exception {
        when(issueService.getAllCategory()).thenReturn(List.of(issueCategory));
        ResultActions response = mockMvc.perform(get("/get-category")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Issue Category 1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Issue Category 1"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void issueApi_getIssuePlaceIsOk() throws Exception {
        IssuePlace issuePlace = IssuePlace.builder()
                .id(1L)
                .name("Issue Place 1")
                .build();
        when(issueService.getAllPlace()).thenReturn(List.of(issuePlace));
        ResultActions response = mockMvc.perform(get("/get-place")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Issue Place 1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Issue Place 1"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void issueApi_getResponsiblePartyIsOk() throws Exception {
        ResponsibleParty responsibleParty = ResponsibleParty.builder()
                .id(1L)
                .name("Responsible Party 1")
                .build();
        when(issueService.getAllResponsibleParty()).thenReturn(List.of(responsibleParty));
        ResultActions response = mockMvc.perform(get("/get-resParty")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Responsible Party 1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Responsible Party 1"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void issueAPi_getPicIsOk() throws Exception {
        // Mock the getUsersByOngoingProject method to return a user when called

        ResultActions response = mockMvc.perform(get("/get-pic")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    if (result.getResponse().getStatus() != 200) {
                        System.err.println("Response content: " + result.getResponse().getContentAsString());
                    }
                });
    }


    @Test
    public void issueApi_createIssueIsOk() throws Exception{
      given(issueService.createIssue(any(IssueFormInput.class))).willReturn(issue);
        ResultActions response = mockMvc.perform(post("/create-issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(issueFormInput)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void issueApi_createResPartyIsOk() throws Exception{
        given(issueService.createResponsibleParty(any(ResponsibleParty.class))).willReturn(responsibleParty);
        ResultActions response = mockMvc.perform(post("/create-resParty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responsibleParty)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void issueApi_createIssuePlaceIsOk() throws Exception{
        given(issueService.createIssuePlace(any(IssuePlace.class))).willReturn(issuePlace);
        ResultActions response = mockMvc.perform(post("/create-place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(issuePlace)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void issueApi_createIssueCategoryIsOk() throws Exception{
        given(issueService.createIssueCategory(any(IssueCategory.class))).willReturn(issueCategory);
        ResultActions response = mockMvc.perform(post("/create-category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(issueCategory)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void issueApi_getAllIssueIsOk() throws Exception{
      when(issueService.getAllIssueDisplayViewObject()).thenReturn(List.of(new AllIssueDisplayViewObject()));
        ResultActions response = mockMvc.perform(get("/get-all-issue")
                .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void issueApi_getIssueDetailsByIdIsOk() throws Exception {
        when(issueService.getIssueDetailsById(any(Long.class))).thenReturn(issueDetailsViewObject);
        ResultActions response = mockMvc.perform(get("/get-issue-details/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Issue 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Issue Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.place").value("Issue Place 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.placeId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responsibleParty").value("Responsible Party 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responsiblePartyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.solved").value(false))

                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void issueApi_getIssueSolutionByIdIsOk() throws Exception {
        when(issueService.getIssueSolutionById(any(Long.class))).thenReturn(issueSolutionViewObject);
        ResultActions response = mockMvc.perform(get("/get-issue-solution/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void issueApi_getIssueByPicIsOk() throws Exception {
        when(issueService.getAllIssueByPic(any(Long.class))).thenReturn(List.of(allIssueDisplayViewObject));
        ResultActions response = mockMvc.perform(get("/get-issue-by-pic/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Issue 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Issue Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].place").value("Issue Place 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].responsibleParty").value("Responsible Party 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].solved").value(false))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void issueApi_addSolutionToIssueIsOk() throws Exception{
        given(issueService.addSolutiontoIssue(any(IssueSolveFormInput.class))).willReturn(issue);
        ResultActions response = mockMvc.perform(post("/add-solution")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(issueSolveFormInput)));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void issueApi_getIssueByCreatedByIsOk() throws Exception {
        when(issueService.getAllIssueByCreatedById(any(Long.class))).thenReturn(List.of(allIssueDisplayViewObject));
        ResultActions response = mockMvc.perform(get("/get-issue-by-createdBy/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Issue 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Issue Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].place").value("Issue Place 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].responsibleParty").value("Responsible Party 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].solved").value(false))
                .andDo(MockMvcResultHandlers.print());
    }
}