package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.formInput.AmountDto;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.AmountService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.blank.projectmanagementsystem.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AmountAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AmountAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AmountService amountService;
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

        private Amount amount;
        private AmountDto amountDto;

        private Project project;
        @BeforeEach
        public void init() {

            project = Project.builder()
                    .id(1L)
                    .name("Project 1")
                    .build();

            amountDto = AmountDto.builder()
                    .id(1L)
                    .projectId(project.getId())
                    .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                    .amount(1000)
                    .build();

            amount = Amount.builder()
                    .id(1L)
                    .project(project)
                    .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                    .amount(1000)
                    .build();
        }

    @Test
    public void amountApi_addOrUpdateAmounts() throws Exception {
        given(amountService.saveOrUpdate(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/amount/add-or-update-amount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" + objectMapper.writeValueAsString(amountDto) + "]")); // Wrap the object in an array

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(amountDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].projectId", CoreMatchers.is(amountDto.getProjectId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].developmentPhase", CoreMatchers.is(amountDto.getDevelopmentPhase().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount", CoreMatchers.is(amountDto.getAmount())))
                .andDo(MockMvcResultHandlers.print());
    }



    @Test
    public void amountApi_getAmounts() throws Exception {
        AmountDto responseDto= AmountDto.builder()
                .id(1L)
                .projectId(1L)
                .developmentPhase(DevelopmentPhase.BASIC_DESIGN)
                .amount(1000)
                .build();

        when(amountService.findByProjectId(1L)).thenReturn(List.of(amount));
        ResultActions response = mockMvc.perform(get("/api/amount/get-amount/1")
                .contentType(MediaType.APPLICATION_JSON)

                .param("projectId", "1")
                .param("developmentPhase", "BASIC_DESIGN")
                .param("amount", "1000")
                .param("id", "1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(responseDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].projectId", CoreMatchers.is(responseDto.getProjectId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].developmentPhase", CoreMatchers.is(responseDto.getDevelopmentPhase().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount", CoreMatchers.is(responseDto.getAmount())))
                .andDo(MockMvcResultHandlers.print());




    }
}