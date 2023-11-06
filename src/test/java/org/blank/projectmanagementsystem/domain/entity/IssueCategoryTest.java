package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueCategoryTest {

    @Test
    public void testConstructorAndGetters() {
        // Create an instance of IssueCategory
        Long id = 1L;
        String name = "Bug";
        IssueCategory issueCategory = new IssueCategory(id, name);

        // Verify the values returned by the getters
        Assertions.assertEquals(id, issueCategory.getId());
        Assertions.assertEquals(name, issueCategory.getName());
    }

    @Test
    public void testSetters() {
        // Create an instance of IssueCategory
        IssueCategory issueCategory = new IssueCategory();

        // Set values using the setters
        Long id = 1L;
        String name = "Bug";
        issueCategory.setId(id);
        issueCategory.setName(name);

        // Verify the values returned by the getters
        Assertions.assertEquals(id, issueCategory.getId());
        Assertions.assertEquals(name, issueCategory.getName());
    }
}