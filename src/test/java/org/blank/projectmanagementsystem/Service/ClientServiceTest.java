package org.blank.projectmanagementsystem.Service;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testSaveClient() {
        Client clientToSave = new Client();
        clientToSave.setId(1L);
        clientToSave.setName("Test Client");
        clientToSave.setEmail("test@test.com");
        clientToSave.setPhoneNumber("1234567890");
        clientToSave.setStatus(true);

        when(clientRepository.save(clientToSave)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> clientService.save(clientToSave));

        verify(clientRepository, times(1)).save(clientToSave);
    }

    @Test
    void testGetAllClients() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("John Doe");
        client1.setEmail("john@example.com");
        client1.setPhoneNumber("1234567890");
        client1.setStatus(true);

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Jane Doe");
        client2.setEmail("jane@example.com");
        client2.setPhoneNumber("0987654321");
        client2.setStatus(true);

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> allClients = clientService.getAllClients();

        assertEquals(2, allClients.size());

        Client retrievedClient1 = allClients.get(0);
        assertEquals("John Doe", retrievedClient1.getName());
        assertEquals("john@example.com", retrievedClient1.getEmail());
        assertEquals("1234567890", retrievedClient1.getPhoneNumber());
        assertTrue(retrievedClient1.isStatus());

        Client retrievedClient2 = allClients.get(1);
        assertEquals("Jane Doe", retrievedClient2.getName());
        assertEquals("jane@example.com", retrievedClient2.getEmail());
        assertEquals("0987654321", retrievedClient2.getPhoneNumber());
        assertTrue(retrievedClient2.isStatus());

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClientById() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");
        client.setEmail("test@test.com");
        client.setPhoneNumber("1234567890");
        client.setStatus(true);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client foundClient = clientService.getClientById(clientId);

        assertEquals("Test Client", foundClient.getName());
        assertEquals("test@test.com", foundClient.getEmail());

        verify(clientRepository, times(1)).findById(clientId);
    }
    @Test
    void testUpdateClient() {
        Long clientId = 1L;

        // Create a client with the initial data
        Client initialClient = new Client();
        initialClient.setId(clientId);
        initialClient.setName("Test Client");
        initialClient.setEmail("test@test.com");
        initialClient.setPhoneNumber("1234567890");
        initialClient.setStatus(true);

        // Create a client with the updated data
        Client updatedClientData = new Client();
        updatedClientData.setName("Updated Client");
        updatedClientData.setEmail("updated@test.com");
        updatedClientData.setPhoneNumber("9876543210");
        updatedClientData.setStatus(false);

        // Mock the repository to return the initial client when findById is called
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(initialClient));

        // Perform the update operation
        clientService.updateClient(clientId, updatedClientData);

        // Verify that the repository's save method was called with the updated client data
        verify(clientRepository, times(1)).save(argThat(savedClient -> {
            // Check if the saved client data matches the updated data
            return savedClient.getId().equals(clientId) &&
                    savedClient.getName().equals(updatedClientData.getName()) &&
                    savedClient.getEmail().equals(updatedClientData.getEmail()) &&
                    savedClient.getPhoneNumber().equals(updatedClientData.getPhoneNumber()) &&
                    savedClient.isStatus() == updatedClientData.isStatus();
        }));
    }




}



