package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    private Client client;
    private Client clientWithBuilder;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("JunctionX");
        client.setEmail("junction@supermarket.com");
        client.setPhoneNumber("0987654321");

        clientWithBuilder = Client.builder()
                .id(2L)
                .name("JunctionCity")
                .email("junctioncity@supermarket.com")
                .phoneNumber("0987654321")
                .build();
    }

    @Test
    public void testGetId() {
        assertEquals(1L, client.getId());
        assertEquals(2L, clientWithBuilder.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("JunctionX", client.getName());
        assertEquals("JunctionCity", clientWithBuilder.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("junction@supermarket.com", client.getEmail());
        assertEquals("junctioncity@supermarket.com", clientWithBuilder.getEmail());
    }

    @Test
    public void testGetPhoneNumber() {
        assertEquals("0987654321", client.getPhoneNumber());
        assertEquals("0987654321", clientWithBuilder.getPhoneNumber());
    }

    @Test
    public void testEquals() {
        Client anotherClient = new Client();
        anotherClient.setId(1L);
        anotherClient.setName("JunctionX");
        anotherClient.setEmail("junction@supermarket.com");
        anotherClient.setPhoneNumber("0987654321");

        Client anotherClientWithBuilder = Client.builder()
                .id(2L)
                .name("JunctionCity")
                .email("junctioncity@supermarket.com")
                .phoneNumber("0987654321")
                .build();

        assertEquals(client, anotherClient);
        assertEquals(clientWithBuilder, anotherClientWithBuilder);
    }

    @Test
    public void testNotEquals() {

        assertNotEquals(client, clientWithBuilder);
    }
}
