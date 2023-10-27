package org.blank.projectmanagementsystem.Service;

import org.blank.projectmanagementsystem.service.ClientService;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void testFindByName() {
        // Arrange
        String clientName = "TestClient";
        Client client = new Client();
        client.setName(clientName);
        when(clientRepository.findByName(clientName)).thenReturn(Optional.of(client));

        // Act
        Optional<Client> foundClient = clientService.findByName(clientName);

        // Assert
        assertEquals(clientName, foundClient.orElseThrow().getName());
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        Client client = new Client();
        client.setEmail(email);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));

        // Act
        Optional<Optional<Client>> foundClient = clientService.findByEmail(email);

        // Assert
        assertEquals(email, foundClient.orElseThrow().get().getEmail());
    }

    @Test
    public void testFindByPhoneNumber() {
        // Arrange
        String phoneNumber = "1234567890";
        Client client = new Client();
        client.setPhoneNumber(phoneNumber);
        when(clientRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(client));

        // Act
        Optional<Optional<Client>> foundClient = clientService.findByPhoneNumber(phoneNumber);

        // Assert
        assertEquals(phoneNumber, foundClient.orElseThrow().get().getPhoneNumber());
    }
}
