package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.service.ArchitectureService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void architectureApi_getArchitectures() throws Exception {
        when(architectureService.getAllArchitectures()).thenReturn(List.of(architecture));
        ResultActions response = mockMvc.perform(get("/architectures")
                .contentType(MediaType.APPLICATION_JSON)
                 .param("id","1")
                .param("name","Architecture 1")
                .param("type","API")
                .param("status","true"));

                response.andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is("Architecture 1")))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", CoreMatchers.is("API")))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", CoreMatchers.is(true)))
                        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void architectureApi_addArchitecture() throws Exception {
        given(architectureService.save(architecture)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-architecture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(architecture)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
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

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void architectureApi_updateArchitecture() throws Exception {
        given(architectureService.getArchitectureById(1L)).willReturn(architecture);
        given(architectureService.save(architecture)).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/architecture/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(architecture)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void architectureApi_updateArchitectureStatus() throws Exception {
        given(architectureService.getArchitectureById(1L)).willReturn(architecture);
        given(architectureService.save(architecture)).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/architecture/status/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("newStatus", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Architecture 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is("API")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

}