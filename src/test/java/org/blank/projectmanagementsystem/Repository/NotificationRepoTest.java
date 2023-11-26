package org.blank.projectmanagementsystem.Repository;


import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.NotificationRepo;
import org.blank.projectmanagementsystem.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationRepoTest {

    @Mock
    private NotificationRepo notificationRepo;

    @InjectMocks
    private NotificationServiceImpl notificationService;  // Assuming you have a service using the repository

    @Test
    public void testFindAllByRecipientEmailOrRecipientUsername() {
        // Given
        String testEmail = "test@example.com";
        String testUsername = "testUser";

        Notification notification1 = new Notification(/* Initialize your Notification object */);
        Notification notification2 = new Notification(/* Initialize another Notification object */);

        List<Notification> mockNotifications = new ArrayList<>();
        mockNotifications.add(notification1);
        mockNotifications.add(notification2);

        // When
        when(notificationRepo.findAllByRecipientEmailOrRecipientUsername(testEmail, testUsername)).thenReturn(mockNotifications);

        // Then
        List<Notification> result = notificationService.findAllByRecipientEmailOrRecipientUsername(testEmail, testUsername);
        assertEquals(mockNotifications, result);
    }


    // Add more test methods as needed for other repository methods
}
