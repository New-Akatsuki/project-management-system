package org.blank.projectmanagementsystem.domain.entity;

import org.blank.projectmanagementsystem.repository.IssueCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class IssueCategoryTest {
    @Mock
    private IssueCategoryRepository issueCategoryRepository;

    @InjectMocks
    private IssueCategory issueCategory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetId() {
        Long expectedId = 1L;
        issueCategory.setId(expectedId);
        assertEquals(expectedId, issueCategory.getId());
    }

    @Test
    public void testGetName() {
        String expectedName = "Test Category";
        issueCategory.setName(expectedName);
        assertEquals(expectedName, issueCategory.getName());
    }

}
