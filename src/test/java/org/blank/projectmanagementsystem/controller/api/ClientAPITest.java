package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.service.ClientService;
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

@WebMvcTest(controllers = ClientAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ClientAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientService clientService;
    @Autowired
    private ObjectMapper objectMapper;
    private Client client;

    @BeforeEach
    public void init() {
        client = Client.builder()
                .id(1L)
                .name("Yephone Aung")
                .email("yephoneaung33002@gmail.com")
                .phoneNumber("09777777777")
                .status(true)
                .build();
    }

    @Test
    public void clientApi_getClients() throws Exception {
        when(clientService.getAllClients()).thenReturn(List.of(client));
        ResultActions response = mockMvc.perform(get("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "Yephone Aung")
                .param("email", "yephoneaung33002@gmail.com")
                .param("phoneNumber", "09777777777")
                .param("status", "true"));

                response.andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Yephone Aung"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("yephoneaung33002@gmail.com"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("09777777777"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(true))
                        .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public  void clientApi_addClient() throws Exception {
        given(clientService.save(client)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Yephone Aung"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yephoneaung33002@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("09777777777"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
                .andDo(MockMvcResultHandlers.print());

    }



    @Test
    public void clientApi_getClientById() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(client);
        ResultActions response = mockMvc.perform(get("/client/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("name", "Yephone Aung")
                .param("email", "yephoneaung33002@gmail.com")
                .param("phoneNumber", "09777777777")
                .param("status", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Yephone Aung"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yephoneaung33002@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("09777777777"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void clientApi_updateClient() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(client);
        given(clientService.save(client)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Yephone Aung"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yephoneaung33002@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("09777777777"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void clientApi_updateClientStatus() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(client);
        given(clientService.save(client)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/client/status/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("newStatus", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Yephone Aung"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yephoneaung33002@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("09777777777"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
                .andDo(MockMvcResultHandlers.print());
    }
}