package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.service.ArchitectureService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@WebMvcTest(controllers = ArchitectureAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ArchitectureAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArchitectureService architectureService;
    @Autowired
    private ObjectMapper objectMapper;

    private Architecture architecture;

    @BeforeEach
    public void init() {
        architecture = Architecture.builder()
                .id(1L)
                .name("Architecture 1")
                .type(ArchitectureType.API)
                .status(true)
                .build();

    }

    @Test
    public void testGetArchitectures() throws Exception {
        // Create sample data for testing
        Architecture architecture = new Architecture(1L, "Architecture 1", ArchitectureType.API, true);
        List<Architecture> architectureList = List.of(architecture);

        // Mock the behavior of the service method
        when(architectureService.getArchitecturesByStatusTrue()).thenReturn(architectureList);

        // Perform the GET request
        ResultActions resultActions = mockMvc.perform(get("/architectures")
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Architecture 1")))
                .andExpect(jsonPath("$[0].type", CoreMatchers.is("API")))
                .andExpect(jsonPath("$[0].status", CoreMatchers.is(true)));
    }

    @Test
    public void architectureApi_addArchitecture() throws Exception {
        given(architectureService.save(architecture)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-architecture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(architecture)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void architectureApi_getArchitectureById() throws Exception {
        when(architectureService.getArchitectureById(1L)).thenReturn(architecture);
        ResultActions response = mockMvc.perform(get("/architecture/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id","1")
                .param("name","Architecture 1")
                .param("type","API")
                .param("status","true"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void architectureApi_updateArchitecture() throws Exception {
        given(architectureService.getArchitectureById(1L)).willReturn(architecture);
        given(architectureService.save(architecture)).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/architecture/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(architecture)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateArchitectureNotFound() throws Exception {
        // Set up for a scenario where the architecture is not found
        Long nonExistingId = 2L;
        Architecture updatedArchitectureRequest = new Architecture(nonExistingId, "Updated Architecture", ArchitectureType.API, false);
        when(architectureService.getArchitectureById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update architecture for a non-existing architecture
        ResultActions resultActions = mockMvc.perform(put("/architecture/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedArchitectureRequest)));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateArchitectureStatus() throws Exception {
        // Create sample data for testing
        Long architectureId = 1L;
        Architecture architecture = new Architecture(architectureId, "Architecture 1", ArchitectureType.API, true);

        // Mock the behavior of the service methods
        when(architectureService.getArchitectureById(architectureId)).thenReturn(architecture);
        when(architectureService.save(any(Architecture.class))).thenReturn(architecture);

        // Perform the PUT request to update status
        ResultActions resultActions = mockMvc.perform(put("/architecture/status/{id}", architectureId)
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(false))); // Expecting the status to be toggled

        // Verify that the service methods were called with the correct arguments
        Mockito.verify(architectureService).getArchitectureById(architectureId);
        Mockito.verify(architectureService).save(any(Architecture.class));
    }

    @Test
    public void testUpdateArchitectureStatusNotFound() throws Exception {
        // Set up for a scenario where the architecture is not found
        Long nonExistingId = 2L;
        when(architectureService.getArchitectureById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update status for a non-existing architecture
        ResultActions resultActions = mockMvc.perform(put("/architecture/status/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testGetArchitectureByIdNotFound() throws Exception {
        // Set up for a scenario where the architecture is not found
        Long nonExistingId = 2L;
        when(architectureService.getArchitectureById(nonExistingId)).thenReturn(null);

        // Perform the GET request
        ResultActions resultActions = mockMvc.perform(get("/architecture/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }
}