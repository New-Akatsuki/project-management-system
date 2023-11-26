package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = SystemOutlineAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SystemOutlineAPITest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SystemOutlineService systemOutlineService;
    @Autowired
    private ObjectMapper objectMapper;

    private SystemOutline systemOutline;

    @BeforeEach
    public void init() {
        systemOutline = SystemOutline.builder()
                .id(1L)
                .name("System Outline 1")
                .status(true)
                .build();
    }

    @Test
    public void systemOutlineApi_getSystemOutlineSuccess() throws Exception {
        when(systemOutlineService.getAllSystemOutlines()).thenReturn(List.of(systemOutline));
        ResultActions response = mockMvc.perform(get("/system-outlines")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "System Outline 1")
                .param("status", "true"));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is("System Outline 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void systemOutlineApi_addSystemOutlineIsSuccess() throws Exception {
        given(systemOutlineService.save(systemOutline)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-system-outline")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(systemOutline)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("System Outline 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void systemOutlineApi_getSystemOutlineByIdIsSuccess() throws Exception {
        when(systemOutlineService.getSystemOutlineById(1L)).thenReturn(systemOutline);
        ResultActions response = mockMvc.perform(get("/system-outline/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "System Outline 1")
                .param("status", "true"));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("System Outline 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void systemOutlineApi_updateSystemOutlineIsSuccess() throws Exception {
        given(systemOutlineService.getSystemOutlineById(1L)).willReturn(systemOutline);
        given(systemOutlineService.save(systemOutline)).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/system-outline/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(systemOutline)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("System Outline 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void systemOutlineApi_updateSystemOutlineStatusIsSuccess() throws Exception {
        given(systemOutlineService.getSystemOutlineById(1L)).willReturn(systemOutline);
        given(systemOutlineService.save(systemOutline)).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/system-outline/status/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("newStatus", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("System Outline 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

}