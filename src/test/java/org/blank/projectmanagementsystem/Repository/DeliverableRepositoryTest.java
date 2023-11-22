package org.blank.projectmanagementsystem.Repository;

import ch.qos.logback.core.testUtil.DelayingListAppender;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliverableRepositoryTest {
    @Mock
    private DeliverableRepository deliverableRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveDeliverable() {
        // Given
        Deliverable deliverableToSave = new Deliverable();
        deliverableToSave.setId(1L);
        deliverableToSave.setName("Deliverable 1");
        deliverableToSave.setStatus(true);

        // When
        when(deliverableRepository.save(deliverableToSave)).thenReturn(deliverableToSave);
        Deliverable savedDeliverable = deliverableRepository.save(deliverableToSave);

        // Then
        assertEquals(deliverableToSave, savedDeliverable);
    }

    @Test
    public void testGetDeliverableById() {
        // Given
        Long deliverableId = 1L;
        Deliverable deliverable = new Deliverable();
        deliverable.setId(deliverableId);
        deliverable.setName("Test Client");
        deliverable.setStatus(true);

        // When
        when(deliverableRepository.findById(deliverableId)).thenReturn(java.util.Optional.of(deliverable));
        Deliverable retrievedDeliverable = deliverableRepository.findById(deliverableId).orElse(null);

        // Then
        assertEquals(deliverable, retrievedDeliverable);
    }

    @Test
    public void testGetAllDeliverables() {
        // Given
        Deliverable deliverable1 = new Deliverable();
        deliverable1.setId(1L);
        deliverable1.setName("John Doe");
        deliverable1.setStatus(true);

        Deliverable deliverable2 = new Deliverable();
        deliverable2.setId(2L);
        deliverable2.setName("Jane Smith");
        deliverable2.setStatus(true);

        List<Deliverable> expectedDeliverables = Arrays.asList(deliverable1, deliverable2);

        // When
        when(deliverableRepository.findAll()).thenReturn(expectedDeliverables);
        List<Deliverable> retrievedDeliverables = deliverableRepository.findAll();
        // Then
        assertEquals(expectedDeliverables, retrievedDeliverables);
    }








}
