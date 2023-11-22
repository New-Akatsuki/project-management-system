package org.blank.projectmanagementsystem.Repository;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveClient() {
        // Given
        Client clientToSave = new Client();
        clientToSave.setId(1L);
        clientToSave.setName("Test Client");
        clientToSave.setEmail("test@test.com");
        clientToSave.setPhoneNumber("1234567890");
        clientToSave.setStatus(true);

        // When
        when(clientRepository.save(clientToSave)).thenReturn(clientToSave);
        Client savedClient = clientRepository.save(clientToSave);

        // Then
        assertEquals(clientToSave, savedClient);
    }
    @Test
    public void testGetClientById() {
        // Given
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");
        client.setEmail("test@test.com");
        client.setPhoneNumber("1234567890");
        client.setStatus(true);

        // When
        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.of(client));
        Client retrievedClient = clientRepository.findById(clientId).orElse(null);

        // Then
        assertEquals(client, retrievedClient);
    }
    @Test
    public void testGetAllClients() {
        // Given
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("John Doe");
        client1.setEmail("john.doe@example.com");
        client1.setPhoneNumber("1234567890");
        client1.setStatus(true);

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Jane Smith");
        client2.setEmail("janesmith@example.com");
        client2.setPhoneNumber("9876543210");
        client2.setStatus(true);

        List<Client> expectedClients = Arrays.asList(client1, client2);

        // When
        when(clientRepository.findAll()).thenReturn(expectedClients);
        List<Client> retrievedClients = clientRepository.findAll();

        // Then
        assertEquals(expectedClients, retrievedClients);
    }
}

