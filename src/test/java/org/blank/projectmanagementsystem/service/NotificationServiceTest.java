package org.blank.projectmanagementsystem.service;



import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.repository.NotificationRepo;
import org.blank.projectmanagementsystem.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepo notificationRepo;

    @InjectMocks
    private NotificationServiceImpl notificationService;  // Assuming you have a service implementation

    @Test
    public void testSaveNotification() {
        // Given
        Notification notificationToSave = new Notification(/* Initialize your Notification object */);

        // When
        when(notificationRepo.save(notificationToSave)).thenReturn(notificationToSave);
        Notification savedNotification = notificationService.saveNotification(notificationToSave);

        // Then
        assertEquals(notificationToSave, savedNotification);
    }

    // Add more test methods as needed for other service methods

    @Test
    public void testDeleteNotificationById() {
        // Given
        Long notificationIdToDelete = 1L;

        // When
        doNothing().when(notificationRepo).deleteById(notificationIdToDelete);
        notificationService.deleteNotificationById(notificationIdToDelete);

        // Then
        verify(notificationRepo, times(1)).deleteById(notificationIdToDelete);
    }


}
