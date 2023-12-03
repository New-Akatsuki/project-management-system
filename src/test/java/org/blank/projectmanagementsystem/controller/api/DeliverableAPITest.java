package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.service.DeliverableService;
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

import javax.xml.transform.Result;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DeliverableAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DeliverableAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeliverableService deliverableService;
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
    public void testGetDeliverables() throws Exception {
        // Create sample data for testing
        Deliverable deliverable1 = new Deliverable(1L, "Deliverable 1", true);
        Deliverable deliverable2 = new Deliverable(2L, "Deliverable 2", true);
        List<Deliverable> deliverables = List.of(deliverable1, deliverable2);

        // Mock the behavior of the service method
        when(deliverableService.getDeliverablesByStatusTrue()).thenReturn(deliverables);

        // Perform the GET request
        ResultActions resultActions = mockMvc.perform(get("/deliverables")
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Deliverable 1")))
                .andExpect(jsonPath("$[0].status", CoreMatchers.is(true)))
                .andExpect(jsonPath("$[1].id", CoreMatchers.is(2)))
                .andExpect(jsonPath("$[1].name", CoreMatchers.is("Deliverable 2")))
                .andExpect(jsonPath("$[1].status", CoreMatchers.is(true)));
    }

    @Test
    public void deliverableApi_addDeliverable() throws Exception {
        given(deliverableService.save(deliverable)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-deliverable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliverable)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(true)))
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

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetDeliverableByIdNotFound() throws Exception {
        // Set up for a scenario where the deliverable is not found
        Long nonExistingId = 2L;
        when(deliverableService.getDeliverableById(nonExistingId)).thenReturn(null);

        // Perform the GET request for a non-existing deliverable
        ResultActions resultActions = mockMvc.perform(get("/deliverable/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateDeliverableNotFound() throws Exception {
        // Set up for a scenario where the deliverable is not found
        Long nonExistingId = 2L;
        Deliverable updatedDeliverableRequest = new Deliverable(nonExistingId, "Updated Deliverable", true);
        when(deliverableService.getDeliverableById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update deliverable for a non-existing deliverable
        ResultActions resultActions = mockMvc.perform(put("/deliverable/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDeliverableRequest)));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void deliverableApi_updateDeliverable() throws Exception {
        when(deliverableService.getDeliverableById(1L)).thenReturn(deliverable);
        given(deliverableService.save(deliverable)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/deliverable/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliverable)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deliverableApi_updateDeliverableStatus() throws Exception {
        when(deliverableService.getDeliverableById(1L)).thenReturn(deliverable);
        given(deliverableService.save(deliverable)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/deliverable/status/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("newStatus", "true"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Deliverable 1")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateDeliverableStatusNotFound() throws Exception {
        // Set up for a scenario where the deliverable is not found
        Long nonExistingId = 2L;
        boolean newStatus = false;
        when(deliverableService.getDeliverableById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update deliverable status for a non-existing deliverable
        ResultActions resultActions = mockMvc.perform(put("/deliverable/status/{id}", nonExistingId)
                .param("newStatus", String.valueOf(newStatus))
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }
}