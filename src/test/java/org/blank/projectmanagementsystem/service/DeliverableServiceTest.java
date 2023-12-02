package org.blank.projectmanagementsystem.service;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.service.impl.DeliverableServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliverableServiceTest {

    @Mock
    private DeliverableRepository deliverableRepository;

    @InjectMocks
    private DeliverableServiceImpl deliverableService;

    @Test
    public void testSaveDeliverable() {
        // Given
        Deliverable deliverableToSave = new Deliverable();
        deliverableToSave.setId(1L);
        deliverableToSave.setName("Test Deliverable");
        deliverableToSave.setStatus(true);

        // When
        when(deliverableRepository.save(deliverableToSave)).thenReturn(deliverableToSave);

        Deliverable savedDeliverable = deliverableService.save(deliverableToSave);

        // Then
        assertEquals("Test Deliverable", savedDeliverable.getName());
        verify(deliverableRepository, times(1)).save(deliverableToSave);
    }

    @Test
    void testGetDeliverableById() {
        // Given
        Long deliverableId = 1L;
        Deliverable deliverable = new Deliverable();
        deliverable.setId(deliverableId);
        deliverable.setName("Test Deliverable");
        deliverable.setStatus(true);

        // When
        when(deliverableRepository.findById(deliverableId)).thenReturn(Optional.of(deliverable));

        Deliverable foundDeliverable = deliverableService.getDeliverableById(deliverableId);

        // Then
        assertEquals("Test Deliverable", foundDeliverable.getName());

        // Verify that the findById method was called with the correct deliverableId
        verify(deliverableRepository, times(1)).findById(deliverableId);
    }



    @Test
    void testGetAllDeliverables() {
        // Given
        Deliverable deliverable1 = new Deliverable();
        deliverable1.setId(1L);
        deliverable1.setName("Deliverable 1");
        deliverable1.setStatus(true);

        Deliverable deliverable2 = new Deliverable();
        deliverable2.setId(2L);
        deliverable2.setName("Deliverable 2");
        deliverable2.setStatus(true);

        // When
        when(deliverableRepository.findAll()).thenReturn(Arrays.asList(deliverable1, deliverable2));

        List<Deliverable> allDeliverables = deliverableService.getAllDeliverables();

        // Then
        assertEquals(2, allDeliverables.size());
        assertEquals("Deliverable 1", allDeliverables.get(0).getName());
        assertEquals("Deliverable 2", allDeliverables.get(1).getName());
        verify(deliverableRepository, times(1)).findAll();
    }


}

