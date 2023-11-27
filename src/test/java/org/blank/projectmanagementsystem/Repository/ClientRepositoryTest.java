package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setName("Test Client");
        client.setEmail("testclient@gmail.com");
        client.setPhoneNumber("1234567890");
        client.setStatus(true);
        client = clientRepository.save(client);
    }

    @AfterEach
    public void tearDown() {
        clientRepository.delete(client);
        client = null;
    }

    @Test
    public void testFindByIdWhenClientExistsThenReturnClient() {
        Optional<Client> foundClient = clientRepository.findById(client.getId());

        assertThat(foundClient).isNotEmpty();
        assertThat(foundClient.get()).isEqualTo(client);
    }

    @Test
    public void testFindByIdWhenClientDoesNotExistThenReturnEmptyOptional() {
        Optional<Client> foundClient = clientRepository.findById(-1L);

        assertThat(foundClient).isEmpty();
    }
}