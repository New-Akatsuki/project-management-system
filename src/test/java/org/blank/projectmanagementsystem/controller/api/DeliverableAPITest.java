package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.DeliverableService;
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

import javax.xml.transform.Result;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = DeliverableAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DeliverableAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeliverableService deliverableService;
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

    private Deliverable deliverable;

    @BeforeEach
    public void init() {
        deliverable = Deliverable.builder()
                .id(1L)
                .name("Deliverable 1")
                .status(true)
                .build();
    }

    @Test
    public void deliverableApi_getDeliverables() throws Exception {
        when(deliverableService.getAllDeliverables()).thenReturn(List.of(deliverable));
        ResultActions response = mockMvc.perform(get("/deliverables")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Deliverable 1")
                .param("status", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deliverableApi_addDeliverable() throws Exception {
        given(deliverableService.save(deliverable)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-deliverable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliverable)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deliverableApi_getDeliverableById() throws Exception {
        when(deliverableService.getDeliverableById(1L)).thenReturn(deliverable);
        ResultActions response = mockMvc.perform(get("/deliverable/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Deliverable 1")
                .param("status", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deliverableApi_updateDeliverable() throws Exception {
        when(deliverableService.getDeliverableById(1L)).thenReturn(deliverable);
        given(deliverableService.save(deliverable)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/deliverable/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliverable)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deliverableApi_updateDeliverableStatus() throws Exception {
        when(deliverableService.getDeliverableById(1L)).thenReturn(deliverable);
        given(deliverableService.save(deliverable)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/deliverable/status/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("newStatus", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }
}