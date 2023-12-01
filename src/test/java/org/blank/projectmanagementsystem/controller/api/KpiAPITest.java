package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.formInput.AmountDto;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;
import org.blank.projectmanagementsystem.service.AmountService;
import org.blank.projectmanagementsystem.service.ArchitectureService;
import org.blank.projectmanagementsystem.service.ReviewCountService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = KpiAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class KpiAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AmountService amountService;
    @MockBean
    private ReviewCountService reviewCountService;
    @Autowired
    private ObjectMapper objectMapper;

    private Amount amount;
    private ReviewCount reviewCount;

    private AmountDto amountDto;

    private ReviewDto reviewDto;



    @BeforeEach
    public void init() {
         amount = Amount.builder()
                .id(1L)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .amount(50)
                .project(null)
                .build();

            reviewCount = ReviewCount.builder()
                    .id(1L)
                    .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                    .reviewerType(ReviewerType.INTERNAL)
                    .project(null)
                    .build();

    }
    @Test
   public void kpiApi_calculateAllKPIs() throws Exception {
        when(amountService.findByProjectId(1L)).thenReturn(List.of(amount));
        when(reviewCountService.findByProjectIdAndDevelopmentPhaseAndReviewerType(1L, DevelopmentPhase.BASIC_DESIGN, ReviewerType.INTERNAL)).thenReturn(reviewCount);
        when(reviewCountService.calculateBasicDesignKpi(1L, DevelopmentPhase.BASIC_DESIGN, ReviewerType.INTERNAL)).thenReturn(0.0);
        mockMvc.perform(get("/calculateAllKPIs/{projectId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.INTERNAL.BASIC_DESIGN", CoreMatchers.is(0.0)))
                .andDo(MockMvcResultHandlers.print());
    }
}