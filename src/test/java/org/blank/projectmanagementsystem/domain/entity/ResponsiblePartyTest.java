package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class ResponsiblePartyTest {

    @Test
    public void testConstructorAndGetters() {
        // Create an instance of ResponsibleParty
        Long id = 1L;
        Set<User> providers = new HashSet<>();
        Client client = new Client();
        ResponsibleParty responsibleParty = new ResponsibleParty(id, providers, client);

        // Verify the values returned by the getters
        Assertions.assertEquals(id, responsibleParty.getId());
        Assertions.assertEquals(providers, responsibleParty.getProviders());
        Assertions.assertEquals(client, responsibleParty.getClient());
    }

    @Test
    public void testSetters() {
        // Create an instance of ResponsibleParty
        ResponsibleParty responsibleParty = new ResponsibleParty();

        // Set values using the setters
        Long id = 1L;
        Set<User> providers = new HashSet<>();
        Client client = new Client();
        responsibleParty.setId(id);
        responsibleParty.setProviders(providers);
        responsibleParty.setClient(client);

        // Verify the values returned by the getters
        Assertions.assertEquals(id, responsibleParty.getId());
        Assertions.assertEquals(providers, responsibleParty.getProviders());
        Assertions.assertEquals(client, responsibleParty.getClient());
    }

    @Test
    public void testProvidersSettersAndGetters() {
        // Create an instance of ResponsibleParty
        ResponsibleParty responsibleParty = new ResponsibleParty();

        // Create a set of providers
        Set<User> providers = new HashSet<>();
        User provider1 = new User();
        User provider2 = new User();
        providers.add(provider1);
        providers.add(provider2);

        // Set the providers using the setter
        responsibleParty.setProviders(providers);

        // Verify the values returned by the getter
        Assertions.assertEquals(providers, responsibleParty.getProviders());
    }
}