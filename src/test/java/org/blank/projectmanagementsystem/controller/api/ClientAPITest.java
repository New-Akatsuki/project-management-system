package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.service.ClientService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void testGetClients() throws Exception {
        // Create sample data for testing
        Client client1 = new Client(1L, "Client 1", "client1@gmail.com", "09999999999", true);
        Client client2 = new Client(2L, "Client 2", "client2@gmail.com","09555555555",true);
        List<Client> clients = List.of(client1, client2);

        // Mock the behavior of the service method
        when(clientService.getClientsByStatusTrue()).thenReturn(clients);

        // Perform the GET request
        ResultActions resultActions = mockMvc.perform(get("/clients")
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Client 1")))
                .andExpect(jsonPath("$[0].status", CoreMatchers.is(true)))
                .andExpect(jsonPath("$[1].id", CoreMatchers.is(2)))
                .andExpect(jsonPath("$[1].name", CoreMatchers.is("Client 2")))
                .andExpect(jsonPath("$[1].status", CoreMatchers.is(true)));
    }

    @Test
    public  void clientApi_addClient() throws Exception {
        given(clientService.save(client)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/add-client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Yephone Aung"))
                .andExpect(jsonPath("$.email").value("yephoneaung33002@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("09777777777"))
                .andExpect(jsonPath("$.status").value(true))
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

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Yephone Aung"))
                .andExpect(jsonPath("$.email").value("yephoneaung33002@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("09777777777"))
                .andExpect(jsonPath("$.status").value(true))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetClientByIdNotFound() throws Exception {
        // Set up for a scenario where the client is not found
        Long nonExistingId = 2L;
        when(clientService.getClientById(nonExistingId)).thenReturn(null);

        // Perform the GET request for a non-existing client
        ResultActions resultActions = mockMvc.perform(get("/client/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void clientApi_updateClient() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(client);
        given(clientService.save(client)).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Yephone Aung"))
                .andExpect(jsonPath("$.email").value("yephoneaung33002@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("09777777777"))
                .andExpect(jsonPath("$.status").value(true))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateClientNotFound() throws Exception {
        // Set up for a scenario where the client is not found
        Long nonExistingId = 2L;
        Client updatedClientRequest = new Client(nonExistingId, "Updated Client", "updated@example.com", "987654321", true);
        when(clientService.getClientById(nonExistingId)).thenReturn(null);

        // Perform the PUT request to update client for a non-existing client
        ResultActions resultActions = mockMvc.perform(put("/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedClientRequest)));

        // Validate the response for a not found scenario
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateClientStatus() throws Exception {
        // Create sample data for testing
        Long clientId = 1L;
        Client client = new Client(clientId, "Client 1", "client@gmail.com", "09888888888", true);

        // Mock the behavior of the service methods
        when(clientService.getClientById(clientId)).thenReturn(client);
        when(clientService.save(any(Client.class))).thenReturn(client);

        // Perform the PUT request to update client status
        ResultActions resultActions = mockMvc.perform(put("/client/status/{id}", clientId)
                .contentType(MediaType.APPLICATION_JSON));

        // Validate the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Client 1")))
                .andExpect(jsonPath("$.status", CoreMatchers.is(false))); // Expecting the status to be toggled

        // Verify that the service methods were called with the correct arguments
        Mockito.verify(clientService).getClientById(clientId);
        Mockito.verify(clientService).save(any(Client.class));
    }
}