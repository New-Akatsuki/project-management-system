package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IssueCategoryTest {

    private IssueCategory issueCategory;

    @BeforeEach
    public void setUp() {
        issueCategory = new IssueCategory();
        issueCategory.setId(1);
        issueCategory.setName("UnitTest Category");
    }

    @Test
    public void testIssueCategoryId() {
        assertEquals(1, issueCategory.getId());
    }

    @Test
    public void testIssueCategoryName() {
        assertEquals("UnitTest Category", issueCategory.getName());
    }

    @Test
    public void testIssueCategoryIdSetter() {
        issueCategory.setId(2);
        assertEquals(2, issueCategory.getId());
    }

    @Test
    public void testIssueCategoryNameSetter() {
        issueCategory.setName("UnitNewTest Category");
        assertEquals("UnitNewTest Category", issueCategory.getName());
    }

    @Test
    public void testIssueCategoryEquality() {
        IssueCategory sameCategory = new IssueCategory();
        sameCategory.setId(1);
        sameCategory.setName("UnitTest Category");

        assertEquals(issueCategory, sameCategory);
    }

    @Test
    public void testIssueCategoryInequality() {
        IssueCategory differentCategory = new IssueCategory();
        differentCategory.setId(2);
        differentCategory.setName("Different Category");

        assertNotEquals(issueCategory, differentCategory);
    }


}
