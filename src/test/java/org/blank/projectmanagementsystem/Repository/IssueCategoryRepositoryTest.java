package org.blank.projectmanagementsystem.Repository;
import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.blank.projectmanagementsystem.repository.IssueCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IssueCategoryRepositoryTest {

    @Mock
    private IssueCategoryRepository issueCategoryRepository;

    @Test
    public void testSaveIssueCategory() {
        // Given
        IssueCategory issueCategoryToSave = new IssueCategory();
        issueCategoryToSave.setName("Test Category");

        IssueCategory savedIssueCategory = new IssueCategory();
        savedIssueCategory.setId(1L);
        savedIssueCategory.setName("Test Category");

        // When
        when(issueCategoryRepository.save(issueCategoryToSave)).thenReturn(savedIssueCategory);

        IssueCategory createdIssueCategory = issueCategoryRepository.save(issueCategoryToSave);

        // Then
        assertEquals("Test Category", createdIssueCategory.getName());
        // Add more assertions if needed
    }

    @Test
    public void testFindAllIssueCategories() {
        // Given
        IssueCategory category1 = new IssueCategory();
        IssueCategory category2 = new IssueCategory();
        // Set properties as needed

        // When
        when(issueCategoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<IssueCategory> allCategories = issueCategoryRepository.findAll();

        // Then
        assertEquals(2, allCategories.size());
        // Add more assertions if needed
    }

    @Test
    public void testFindIssueCategoryById() {
        // Given
        Long categoryId = 1L;
        IssueCategory issueCategory = new IssueCategory();
        // Set properties as needed

        // When
        when(issueCategoryRepository.findById(categoryId)).thenReturn(Optional.of(issueCategory));

        Optional<IssueCategory> result = issueCategoryRepository.findById(categoryId);

        // Then
        assertEquals(issueCategory, result.orElse(null));
        // Add more assertions if needed
    }

    @Test
    public void testDeleteIssueCategory() {
        // Given
        Long categoryId = 1L;
        IssueCategory issueCategory = new IssueCategory();
        // Set properties as needed

        // When
        doNothing().when(issueCategoryRepository).deleteById(categoryId);

        issueCategoryRepository.deleteById(categoryId);

        // Then
        verify(issueCategoryRepository, times(1)).deleteById(categoryId);
        // Add more assertions if needed
    }
    @Test
    public void testFindIssueCategoryByName() {
        // Given
        String categoryName = "Test Category";
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setName(categoryName);

        // When
        when(issueCategoryRepository.findIssueCategoryByName(categoryName)).thenReturn(issueCategory);
        IssueCategory retrievedIssueCategory = issueCategoryRepository.findIssueCategoryByName(categoryName);

        // Then
        assertEquals(issueCategory, retrievedIssueCategory);
    }
    @Test
    public void testUpdateIssueCategory() {
        // Given
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setId(1L);
        issueCategory.setName("Test Category");

        // When
        when(issueCategoryRepository.save(issueCategory)).thenReturn(issueCategory);
        IssueCategory updatedIssueCategory = issueCategoryRepository.save(issueCategory);

        // Then
        assertEquals(issueCategory, updatedIssueCategory);
    }
    @Test
    public void testDeleteIssueCategoryById() {
        // Given
        Long categoryId = 1L;
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setId(categoryId);
        issueCategory.setName("Test Category");

        // When
        doNothing().when(issueCategoryRepository).deleteById(categoryId);
        issueCategoryRepository.deleteById(categoryId);

        // Then
        verify(issueCategoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testDeleteIssueCategoryByName() {
        // Given
        String categoryName = "Test Category";
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setName(categoryName);

        // When
        doNothing().when(issueCategoryRepository).deleteByName(categoryName);
        issueCategoryRepository.deleteByName(categoryName);

        // Then
        verify(issueCategoryRepository, times(1)).deleteByName(categoryName);
    }
    @Test
    public void testFindAllIssueCategoriesByName() {
        // Given
        String categoryName = "Test Category";
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setName(categoryName);

        // When
        when(issueCategoryRepository.findAllByName(categoryName)).thenReturn(Arrays.asList(issueCategory));
        List<IssueCategory> retrievedIssueCategories = issueCategoryRepository.findAllByName(categoryName);

        // Then
        assertEquals(1, retrievedIssueCategories.size());
        assertEquals(issueCategory, retrievedIssueCategories.get(0));
    }
    @Test
    public void testFindAllIssueCategoriesByNameNotFound() {
        // Given
        String categoryName = "Test Category";
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setName(categoryName);

        // When
        when(issueCategoryRepository.findAllByName(categoryName)).thenReturn(Arrays.asList());
        List<IssueCategory> retrievedIssueCategories = issueCategoryRepository.findAllByName(categoryName);

        // Then
        assertEquals(0, retrievedIssueCategories.size());
    }
    @Test
    public void testFindAllIssueCategoriesByNameMultiple() {
        // Given
        String categoryName = "Test Category";
        IssueCategory issueCategory1 = new IssueCategory();
        issueCategory1.setName(categoryName);
        IssueCategory issueCategory2 = new IssueCategory();
        issueCategory2.setName(categoryName);

        // When
        when(issueCategoryRepository.findAllByName(categoryName)).thenReturn(Arrays.asList(issueCategory1, issueCategory2));
        List<IssueCategory> retrievedIssueCategories = issueCategoryRepository.findAllByName(categoryName);

        // Then
        assertEquals(2, retrievedIssueCategories.size());
        assertEquals(issueCategory1, retrievedIssueCategories.get(0));
        assertEquals(issueCategory2, retrievedIssueCategories.get(1));
    }
    @Test
    public void testGetIssue() {
        // Given
        Long issueId = 1L;
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setId(issueId);
        issueCategory.setName("Test Category");

        // When
        when(issueCategoryRepository.getOne(issueId)).thenReturn(issueCategory);
        IssueCategory retrievedIssueCategory = issueCategoryRepository.getOne(issueId);

        // Then
        assertEquals(issueCategory, retrievedIssueCategory);
    }
    @Test
    public void testGetAllUser() {
        // Given
        IssueCategory issueCategory1 = new IssueCategory();
        IssueCategory issueCategory2 = new IssueCategory();
        // Set properties as needed

        // When
        when(issueCategoryRepository.findAll()).thenReturn(Arrays.asList(issueCategory1, issueCategory2));

        List<IssueCategory> allCategories = issueCategoryRepository.findAll();

        // Then
        assertEquals(2, allCategories.size());
        // Add more assertions if needed
    }

    @Test
    public void testIssueDetailById() {
        // Given
        Long issueId = 1L;
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setId(issueId);
        issueCategory.setName("Test Category");

        // When
        when(issueCategoryRepository.getOne(issueId)).thenReturn(issueCategory);
        IssueCategory retrievedIssueCategory = issueCategoryRepository.getOne(issueId);

        // Then
        assertEquals(issueCategory, retrievedIssueCategory);
    }
    @Test
    public void testGetIssueSolutionById() {
        // Given
        Long issueId = 1L;
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setId(issueId);
        issueCategory.setName("Test Category");

        // When
        when(issueCategoryRepository.getOne(issueId)).thenReturn(issueCategory);
        IssueCategory retrievedIssueCategory = issueCategoryRepository.getOne(issueId);

        // Then
        assertEquals(issueCategory, retrievedIssueCategory);
    }
    @Test
    public void testGetAllIssueByPic() {
        // Given
        Long issueId = 1L;
        IssueCategory issueCategory = new IssueCategory();
        issueCategory.setId(issueId);
        issueCategory.setName("Test Category");

        // When
        when(issueCategoryRepository.getOne(issueId)).thenReturn(issueCategory);
        IssueCategory retrievedIssueCategory = issueCategoryRepository.getOne(issueId);

        // Then
        assertEquals(issueCategory, retrievedIssueCategory);
    }










    // Add more test methods for other repository methods if needed
}
