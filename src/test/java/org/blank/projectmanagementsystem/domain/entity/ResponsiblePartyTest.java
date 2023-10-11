package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResponsiblePartyTest {

    private ResponsibleParty responsibleParty;
    private User provider;
    private Client client;

    @BeforeEach
    public void setUp() {
        provider = new User(/* pass necessary parameters to the User constructor */);
        client = new Client(/* pass necessary parameters to the Client constructor */);

        responsibleParty = ResponsibleParty.builder()
                .id(1L)
                .Provider(provider)
                .client(client)
                .build();
    }

    @Test
    public void testGetId() {
        assertEquals(1L, responsibleParty.getId());
    }

    @Test
    public void testGetProvider() {
        assertNotNull(responsibleParty.getProvider());
        assertEquals(provider, responsibleParty.getProvider());
    }

    @Test
    public void testGetClient() {
        assertNotNull(responsibleParty.getClient());
        assertEquals(client, responsibleParty.getClient());
    }

    @Test
    public void testEquals() {
        ResponsibleParty anotherResponsibleParty = ResponsibleParty.builder()
                .id(1L)
                .Provider(provider)
                .client(client)
                .build();

        assertEquals(responsibleParty, anotherResponsibleParty);
    }

    @Test
    public void testNotEquals() {
        ResponsibleParty differentResponsibleParty = ResponsibleParty.builder()
                .id(2L)
                .Provider(new User())
                .client(new Client())
                .build();

        assertNotEquals(responsibleParty, differentResponsibleParty);
    }
}

