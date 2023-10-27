package org.blank.projectmanagementsystem.Service;

import org.springframework.boot.test.context.SpringBootTest;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class DeliverableServiceTest {
    @Test
    public void testCreateDeliverable() {
        // Arrange
        DeliverableService deliverableService = mock(DeliverableService.class);
        Deliverable deliverable = new Deliverable();

        // Act
        when(deliverableService.createDeliverable(deliverable)).thenReturn(deliverable);
        Deliverable createdDeliverable = deliverableService.createDeliverable(deliverable);

        // Assert
        assertNotNull(createdDeliverable);
        assertEquals(deliverable, createdDeliverable);
        verify(deliverableService, times(1)).createDeliverable(deliverable);
    }

    @Test
    public void testUpdateDeliverable() {
        // Arrange
        DeliverableService deliverableService = mock(DeliverableService.class);
        Deliverable deliverable = new Deliverable();

        // Act
        when(deliverableService.updateDeliverable(deliverable)).thenReturn(deliverable);
        Deliverable updatedDeliverable = deliverableService.updateDeliverable(deliverable);

        // Assert
        assertNotNull(updatedDeliverable);
        assertEquals(deliverable, updatedDeliverable);
        verify(deliverableService, times(1)).updateDeliverable(deliverable);
    }

    @Test
    public void testDeleteDeliverable() {
        // Arrange
        DeliverableService deliverableService = mock(DeliverableService.class);
        int deliverableId = 1;

        // Act
        deliverableService.deleteDeliverable(deliverableId);

        // Assert
        verify(deliverableService, times(1)).deleteDeliverable(deliverableId);
    }

    @Test
    public void testGetDeliverable() {
        // Arrange
        DeliverableService deliverableService = mock(DeliverableService.class);
        int deliverableId = 1;
        Deliverable deliverable = new Deliverable();

        // Act
        when(deliverableService.getDeliverable(deliverableId)).thenReturn(deliverable);
        Deliverable retrievedDeliverable = deliverableService.getDeliverable(deliverableId);

        // Assert
        assertNotNull(retrievedDeliverable);
        assertEquals(deliverable, retrievedDeliverable);
        verify(deliverableService, times(1)).getDeliverable(deliverableId);
    }

}
