package org.blank.projectmanagementsystem.service.impl;
import com.pusher.rest.Pusher;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.repository.NotificationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepo notificationRepo;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testSaveNotification() {
        Notification notification = Notification.builder().id(1L).build();
        Notification savedNotification = notificationService.saveNotification(notification);
        assertEquals(1L,savedNotification.getId());
    }

    @Test
    void testGetNotifications() {
        String username = "testUser";
        // Mocking SecurityContextHolder and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        // Set up the desired behavior
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set the mocked SecurityContext in SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        // Perform the test
        List<Notification> notifications = notificationService.getNotifications();

        assertEquals(0, notifications.size());
        // Add more assertions if needed
    }

    @Test
    void testGetNotificationById() {
        String username = "testUser";
        // Mocking SecurityContextHolder and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        // Set up the desired behavior
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set the mocked SecurityContext in SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        List<Notification> notifications = notificationService.getNotificationById();
        assertEquals(0, notifications.size());
    }

    @Test
    void testDeleteNotificationById() {
        Long notificationId = 1L;
        // Perform the test
        notificationService.deleteNotificationById(notificationId);

    }

    @Test
    void testSetNotificationIsRead() {
        when(notificationRepo.getReferenceById(anyLong())).thenReturn(new Notification());

        // Mock the save method
        when(notificationRepo.save(any())).thenReturn(new Notification());
        // Perform the test
        notificationService.setNotificationIsRead(anyLong());

        // Assertions
        // Add more assertions if needed
    }

    @Test
    public void testSendNotification() {
        // Mock the Pusher class
        try (Pusher pusherMock = mock(Pusher.class)) {
            // Create a sample notification
            Notification notification = new Notification(/* fill in constructor parameters */);

            // Call the method to send the notification
            notificationService.sendNotification(notification, 123);

            // Verify that the trigger method was called with the expected parameters
        } catch (Exception e) {
            // Handle exceptions or fail the test as needed
            e.printStackTrace();
        }
    }

}
