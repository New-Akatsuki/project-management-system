package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SystemOutlineAPI.class})
@ExtendWith(SpringExtension.class)
class SystemOutlineAPITest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private SystemOutlineAPI systemOutlineAPI;

    @MockBean
    private SystemOutlineService systemOutlineService;

    /**
     * Method under test: {@link SystemOutlineAPI#addSystemOutline(SystemOutline)}
     */
    @Test
    void testAddSystemOutline() throws Exception {
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setId(1L);
        systemOutline.setName("Name");
        systemOutline.setStatus(true);
        when(systemOutlineService.save(Mockito.<SystemOutline>any())).thenReturn(systemOutline);

        SystemOutline systemOutline2 = new SystemOutline();
        systemOutline2.setId(1L);
        systemOutline2.setName("Name");
        systemOutline2.setStatus(true);
        String content = (new ObjectMapper()).writeValueAsString(systemOutline2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add-system-outline")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(systemOutlineAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<SystemOutline><id>1</id><name>Name</name><status>true</status></SystemOutline>"));
    }

    /**
     * Method under test: {@link SystemOutlineAPI#getSystemOutlineById(Long)}
     */
    @Test
    void testGetSystemOutlineById() throws Exception {
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setId(1L);
        systemOutline.setName("Name");
        systemOutline.setStatus(true);
        when(systemOutlineService.getSystemOutlineById(Mockito.<Long>any())).thenReturn(systemOutline);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/system-outline/{id}", 1L);
        MockMvcBuilders.standaloneSetup(systemOutlineAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<SystemOutline><id>1</id><name>Name</name><status>true</status></SystemOutline>"));
    }

    /**
     * Method under test:  {@link SystemOutlineAPI#getSystemOutlines()}
     */
    @Test
    void testGetSystemOutlines() throws Exception {
        when(systemOutlineService.getAllSystemOutlineByStatusTrue()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/system-outlines");
        MockMvcBuilders.standaloneSetup(systemOutlineAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test:
     * {@link SystemOutlineAPI#updateSystemOutline(SystemOutline)}
     */
    @Test
    void testUpdateSystemOutline() throws Exception {
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setId(1L);
        systemOutline.setName("Name");
        systemOutline.setStatus(true);

        SystemOutline systemOutline2 = new SystemOutline();
        systemOutline2.setId(1L);
        systemOutline2.setName("Name");
        systemOutline2.setStatus(true);
        when(systemOutlineService.save(Mockito.<SystemOutline>any())).thenReturn(systemOutline2);
        when(systemOutlineService.getSystemOutlineById(Mockito.<Long>any())).thenReturn(systemOutline);

        SystemOutline systemOutline3 = new SystemOutline();
        systemOutline3.setId(1L);
        systemOutline3.setName("Name");
        systemOutline3.setStatus(true);
        String content = (new ObjectMapper()).writeValueAsString(systemOutline3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/system-outline/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(systemOutlineAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<SystemOutline><id>1</id><name>Name</name><status>true</status></SystemOutline>"));
    }

    /**
     * Method under test:
     * {@link SystemOutlineAPI#updateSystemOutlineStatus(Long, boolean)}
     */
    @Test
    void testUpdateSystemOutlineStatus() throws Exception {
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setId(1L);
        systemOutline.setName("Name");
        systemOutline.setStatus(true);

        SystemOutline systemOutline2 = new SystemOutline();
        systemOutline2.setId(1L);
        systemOutline2.setName("Name");
        systemOutline2.setStatus(true);
        when(systemOutlineService.save(Mockito.<SystemOutline>any())).thenReturn(systemOutline2);
        when(systemOutlineService.getSystemOutlineById(Mockito.<Long>any())).thenReturn(systemOutline);
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/system-outline/status/{id}", 1L);
        MockHttpServletRequestBuilder requestBuilder = putResult.param("newStatus", String.valueOf(true));
        MockMvcBuilders.standaloneSetup(systemOutlineAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<SystemOutline><id>1</id><name>Name</name><status>true</status></SystemOutline>"));
    }
    @Test
    public void testGetSystemOutlineByIdNotFound() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(systemOutlineAPI).build();

        // Create sample data for testing
        Long nonExistingId = 999L;

        // Mock the behavior of the service method for a non-existing ID
        when(systemOutlineService.getSystemOutlineById(nonExistingId)).thenReturn(null);

        // Perform the GET request for a non-existing system outline ID
        mockMvc.perform(get("/system-outline/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSystemOutlineNotFound() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(systemOutlineAPI).build();
        objectMapper = new ObjectMapper();
        // Create sample data for testing
        Long nonExistingId = 999L;
        SystemOutline nonExistingSystemOutline = new SystemOutline();
        nonExistingSystemOutline.setId(nonExistingId);
        nonExistingSystemOutline.setName("Non-Existing System Outline");

        // Mock the behavior of the service methods for a non-existing ID
        when(systemOutlineService.getSystemOutlineById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update a non-existing system outline
        mockMvc.perform(put("/system-outline/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistingSystemOutline)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSystemOutlineStatusNotFound() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(systemOutlineAPI).build();
        // Create sample data for testing
        Long nonExistingId = 999L;

        // Mock the behavior of the service methods for a non-existing ID
        when(systemOutlineService.getSystemOutlineById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update the status of a non-existing system outline
        mockMvc.perform(put("/system-outline/status/{id}", nonExistingId)
                        .param("newStatus", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}