package org.blank.projectmanagementsystem.Repository;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByName() {
        Client client = new Client();
        client.setName("TestClient");
        entityManager.persistAndFlush(client);

        Optional<Client> foundClient = clientRepository.findByName("TestClient");
        assertEquals("TestClient", foundClient.orElseThrow().getName());
    }

    @Test
    public void testFindByEmail() {
        Client client = new Client();
        client.setEmail("test@example.com");
        entityManager.persistAndFlush(client);

        Optional<Client> foundClient = clientRepository.findByEmail("test@example.com");
        assertEquals("test@example.com", foundClient.get().getEmail());
    }

    @Test
    public void testFindByPhoneNumber() {
        Client client = new Client();
        client.setPhoneNumber("1234567890");
        entityManager.persistAndFlush(client);

        Optional<Client> foundClient = clientRepository.findByPhoneNumber("1234567890");
        assertEquals("1234567890", foundClient.get().getPhoneNumber());
    }
}
