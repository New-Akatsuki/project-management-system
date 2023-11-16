package org.blank.projectmanagementsystem.Service;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientService clientService;

    @Test
    public void testSaveClient() {
        Client clientToSave = new Client();
        clientToSave.setId(1L);
        clientToSave.setName("Test Client");
        clientToSave.setEmail("test@test.com");
        clientToSave.setPhoneNumber("1234567890");
        clientToSave.setStatus(true);

        when(clientService.save(clientToSave)).thenReturn(clientToSave);

        Client savedClient = clientService.save(clientToSave);

        verify(clientService, times(1)).save(clientToSave);
        assertEquals(clientToSave, savedClient);
    }

    @Test
    public void testGetAllClients() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Client 1");
        client1.setEmail("client1@test.com");
        client1.setPhoneNumber("1234567890");
        client1.setStatus(true);

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Client 2");
        client2.setEmail("client2@test.com");
        client2.setPhoneNumber("9876543210");
        client2.setStatus(false);

        List<Client> clients = Arrays.asList(client1, client2);

        when(clientService.getAllClients()).thenReturn(clients);

        List<Client> returnedClients = clientService.getAllClients();

        verify(clientService, times(1)).getAllClients();
        assertEquals(clients, returnedClients);
    }

    @Test
    public void testGetClientById() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");
        client.setEmail("test@test.com");
        client.setPhoneNumber("1234567890");
        client.setStatus(true);

        when(clientService.getClientById(clientId)).thenReturn(client);

        Optional<Client> returnedClient = Optional.ofNullable(clientService.getClientById(clientId));

        assertEquals(client, returnedClient.orElse(null));
    }

    @Test
    public void testUpdateClientStatus() {
        Long clientId = 1L;
        boolean newStatus = false;

        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");
        client.setEmail("test@test.com");
        client.setPhoneNumber("1234567890");
        client.setStatus(true);

        when(clientService.updateClientStatus(clientId, newStatus)).thenReturn(client);

        Client updatedClient = clientService.updateClientStatus(clientId, newStatus);

        verify(clientService, times(1)).updateClientStatus(clientId, newStatus);
        assertEquals(newStatus, updatedClient.isStatus());
    }
}

