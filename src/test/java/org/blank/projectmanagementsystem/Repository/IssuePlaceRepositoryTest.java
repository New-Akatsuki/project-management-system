package org.blank.projectmanagementsystem.Repository;
import org.blank.projectmanagementsystem.domain.entity.IssuePlace;
import org.blank.projectmanagementsystem.repository.IssuePlaceRepository;
import org.blank.projectmanagementsystem.service.impl.IssueServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssuePlaceRepositoryTest {

    @Mock
    private IssuePlaceRepository issuePlaceRepository;

    @InjectMocks
    private IssueServiceImpl issuePlaceService;  // Assuming you have a service using the repository

    @Test
    public void testGetAllIssuePlaces() {
        // Given
        List<IssuePlace> mockIssuePlaces = List.of(
                new IssuePlace(1L, "Place1"),
                new IssuePlace(2L, "Place2")
                // Add more as needed
        );

        // When
        when(issuePlaceRepository.findAll()).thenReturn(mockIssuePlaces);

        // Then
        List<IssuePlace> result = issuePlaceService.getAllIssuePlaces();
        assertEquals(mockIssuePlaces, result);
    }

    @Test
    public void testGetIssuePlaceById() {
        // Given
        Long placeId = 1L;
        IssuePlace mockIssuePlace = new IssuePlace(placeId, "Test Place");

        // When
        when(issuePlaceRepository.findById(placeId)).thenReturn(java.util.Optional.of(mockIssuePlace));

        // Then
        IssuePlace result = issuePlaceService.getIssuePlaceById(placeId);
        assertEquals(mockIssuePlace, result);
    }

    @Test
    public void testGetAllCategory() {
        // Given
        List<IssuePlace> mockIssuePlaces = List.of(
                new IssuePlace(1L, "Place1"),
                new IssuePlace(2L, "Place2")
                // Add more as needed
        );

        // When
        when(issuePlaceRepository.findAll()).thenReturn(mockIssuePlaces);

        // Then
        List<IssuePlace> result = issuePlaceService.getAllIssuePlaces();
        assertEquals(mockIssuePlaces, result);
    }
    @Test
    public void testGetAllPlace() {
        // Given
        List<IssuePlace> mockIssuePlaces = List.of(
                new IssuePlace(1L, "Place1"),
                new IssuePlace(2L, "Place2")
                // Add more as needed
        );

        // When
        when(issuePlaceRepository.findAll()).thenReturn(mockIssuePlaces);

        // Then
        List<IssuePlace> result = issuePlaceService.getAllIssuePlaces();
        assertEquals(mockIssuePlaces, result);
    }

    @Test
    public void testGetAllResponsibleParty() {
        // Given
        List<IssuePlace> mockIssuePlaces = List.of(
                new IssuePlace(1L, "Place1"),
                new IssuePlace(2L, "Place2")
                // Add more as needed
        );

        // When
        when(issuePlaceRepository.findAll()).thenReturn(mockIssuePlaces);

        // Then
        List<IssuePlace> result = issuePlaceService.getAllIssuePlaces();
        assertEquals(mockIssuePlaces, result);
    }
    @Test
    public void testGetAllIssueDisplayViewObject() {
        // Given
        List<IssuePlace> mockIssuePlaces = List.of(
                new IssuePlace(1L, "Place1"),
                new IssuePlace(2L, "Place2")
                // Add more as needed
        );

        // When
        when(issuePlaceRepository.findAll()).thenReturn(mockIssuePlaces);

        // Then
        List<IssuePlace> result = issuePlaceService.getAllIssuePlaces();
        assertEquals(mockIssuePlaces, result);
    }

    // Add more test methods as needed for other repository methods
}

