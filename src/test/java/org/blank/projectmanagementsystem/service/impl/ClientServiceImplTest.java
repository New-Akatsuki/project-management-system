package org.blank.projectmanagementsystem.service.impl;
import org.blank.projectmanagementsystem.repository.ClientRepository;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveClient() {
        // Create a mock client
        Client client = new Client();
        // Mock the behavior of the repository
        when(clientRepository.save(client)).thenReturn(client);

        // Test the saveClient method
        Client savedClient = clientService.save(client);
        //Verify that the saved client matches the mock
        assertEquals(client, savedClient);
    }

    @Test
    void testGetAllClients() {
        //Mock a list of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());

        // Mock the behavior of the repository
        when(clientRepository.findAll()).thenReturn(clients);

        //Test the getAllClients method
        List<Client> retrievedClients = clientService.getAllClients();

        // Verify that the retrieved list size matches the mocked list size
        assertEquals(clients.size(), retrievedClients.size());

    }
    @Test
    void testGetClientById() {
        // Create a mock client
        Long id = 1L;
        Client client = new Client();
        client.setId(id);

        // Mock the behavior of the repository
        when(clientRepository.findById(id)).thenReturn(java.util.Optional.of(client));

        // Test the getClientById method
        Client retrievedClient = clientService.getClientById(id);

        // Verify that the retrieved client matches the mock
        assertEquals(id, retrievedClient.getId());
    }
}
