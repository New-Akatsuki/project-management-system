package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssuePlaceTest {

    @Test
    public void testConstructorAndGetters() {
        // Create an instance of IssuePlace
        Long id = 1L;
        String name = "Office";
        IssuePlace issuePlace = new IssuePlace(id, name);

        // Verify the values returned by the getters
        Assertions.assertEquals(id, issuePlace.getId());
        Assertions.assertEquals(name, issuePlace.getName());
    }

    @Test
    public void testSetters() {
        // Create an instance of IssuePlace
        IssuePlace issuePlace = new IssuePlace();

        // Set values using the setters
        Long id = 1L;
        String name = "Office";
        issuePlace.setId(id);
        issuePlace.setName(name);

        // Verify the values returned by the getters
        Assertions.assertEquals(id, issuePlace.getId());
        Assertions.assertEquals(name, issuePlace.getName());
    }
}