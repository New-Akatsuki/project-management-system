package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;
import org.blank.projectmanagementsystem.service.ReviewCountService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ReviewAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ReviewAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewCountService reviewCountService;
    @Autowired
    private ObjectMapper objectMapper;

    private ReviewCount reviewCount;
    private ReviewDto reviewDto;
    private Project project;


    @BeforeEach
    public void init(){
        project = Project.builder()
                .id(1L)
                .name("Project 1")
                .build();

        reviewCount = ReviewCount.builder()
                .id(1L)
                .project(project)
                .count(2)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .reviewerType(ReviewerType.EXTERNAL)
                .build();

        reviewDto = ReviewDto.builder()
                .id(1L)
                .projectId(project.getId())
                .count(2)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .reviewerType(ReviewerType.EXTERNAL)
                .build();
    }

    @Test
    public void reviewApi_addOrUpdateReviewCountScueess() throws Exception {
        given(reviewCountService.saveOrUpdate(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/api/review/add-review")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[" + objectMapper.writeValueAsString(reviewDto) + "]")); // Wrap the object in an array

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(reviewDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].projectId", CoreMatchers.is(reviewDto.getProjectId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].count", CoreMatchers.is(reviewDto.getCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].developmentPhase", CoreMatchers.is(reviewDto.getDevelopmentPhase().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reviewerType", CoreMatchers.is(reviewDto.getReviewerType().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void reviewApi_getReviewSuccess() throws Exception {
        ReviewDto responseDto = ReviewDto.builder()
                .id(1L)
                .projectId(project.getId())
                .count(2)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .reviewerType(ReviewerType.EXTERNAL)
                .build();

        when(reviewCountService.findByProjectId(1L)).thenReturn(List.of(reviewCount));

        ResultActions response = mockMvc.perform(get("/api/review/get-review/1/EXTERNAL")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[" + objectMapper.writeValueAsString(reviewDto) + "]")); // Wrap the object in an array

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(reviewDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].projectId", CoreMatchers.is(reviewDto.getProjectId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].count", CoreMatchers.is(reviewDto.getCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].developmentPhase", CoreMatchers.is(reviewDto.getDevelopmentPhase().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reviewerType", CoreMatchers.is(reviewDto.getReviewerType().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

}