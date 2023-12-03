package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeliverableServiceImplTest {
    @Mock
    private DeliverableRepository deliverableRepository;

    @InjectMocks
    private DeliverableServiceImpl deliverableService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testSaveDeliverable() {
        // Create a mock deliverable
        Deliverable deliverable = new Deliverable();
        // Mock the behavior of the repository
        when(deliverableRepository.save(deliverable)).thenReturn(deliverable);

        // Test the saveDeliverable method
        Deliverable savedDeliverable = deliverableService.save(deliverable);
        //Verify that the saved deliverable matches the mock
        assertEquals(deliverable, savedDeliverable);
    }
    @Test
    void testGetDeliverableById() {
        // Create a mock deliverable
        Long id=1L;
        Deliverable deliverable = new Deliverable();
        deliverable.setId(id);

        // Mock the behavior of the repository
        when(deliverableRepository.findById(id)).thenReturn(Optional.of(deliverable));


        // Test the getDeliverableById method
        Deliverable retrievedDeliverable = deliverableService.getDeliverableById(id);

        //Verify that the saved deliverable matches the mock
        assertEquals(id,retrievedDeliverable.getId());
    }
    @Test
    void testFindById(){
        // Create a mock deliverable
        Long id=1L;
        Deliverable deliverable = new Deliverable();
        deliverable.setId(id);

        // Mock the behavior of the repository
        when(deliverableRepository.findById(id)).thenReturn(Optional.of(deliverable));

        //Test the findById method
        Deliverable foundDeliverable = deliverableService.findById(id);

        //Verify that the found deliverable matches the mock
        assertEquals(id,foundDeliverable.getId());
    }
    @Test
    void testGetAllDeliverables(){
        // Mock a list of deliverables
        List<Deliverable> deliverables = new ArrayList<>();
        deliverables.add(new Deliverable());
        deliverables.add(new Deliverable());
        // Mock the behavior of the repository
        when(deliverableRepository.findAll()).thenReturn(deliverables);
        // Test the getAllDeliverables() method
        List<Deliverable> retrievedDeliverables = deliverableService.getAllDeliverables();
        // Verify that the retrieved list size matches the mocked list size
        assertEquals(deliverables.size(), retrievedDeliverables.size());
    }

    @Test
    public void testGetDeliverablesByStatusTrue() {
        // Arrange
        Deliverable deliverable1 = new Deliverable(/* construct your object here */);
        Deliverable deliverable2 = new Deliverable(/* construct your object here */);

        List<Deliverable> expectedDeliverables = List.of(deliverable1, deliverable2);

        // Mocking the behavior of the repository
        when(deliverableRepository.findByStatusIsTrue()).thenReturn(expectedDeliverables);

        // Act
        List<Deliverable> actualDeliverables = deliverableService.getDeliverablesByStatusTrue();

        // Assert
        // Add assertions based on your specific requirements
        assertEquals(expectedDeliverables.size(), actualDeliverables.size());
        assertEquals(expectedDeliverables, actualDeliverables);

        // Verify that the repository method was called
        verify(deliverableRepository, times(1)).findByStatusIsTrue();
    }


}
