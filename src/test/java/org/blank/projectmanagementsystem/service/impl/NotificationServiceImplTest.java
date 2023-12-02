package org.blank.projectmanagementsystem.service.impl;

import com.pusher.rest.Pusher;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.repository.NotificationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {
    @Mock
    private NotificationRepo notificationRepo;
    @InjectMocks
    private NotificationServiceImpl notificationService;

   @BeforeEach
    void setUp(){
       MockitoAnnotations.initMocks(this);
       SecurityContextHolder.getContext().getAuthentication().getName();

   }
    @Test
    void testSaveNotification() {
        Notification notification = new Notification();
        doNothing().when(notificationRepo).save(notification);
        notificationService.saveNotification(notification);
    }
    @Test
    void testSendNotification() throws Exception {
        Notification notification = new Notification();
        long id = 1L;
        String appId = "1682457";
        String key = "45b9b41cab6ad01f6264";
        String secret = "5aba9676c16d8ef8500f";
        String cluster = "ap1";

        try (Pusher pusher = new Pusher(appId, key, secret)) {
            doNothing().when(notificationRepo).save(notification);
            notificationService.sendNotification(notification, id);
        }
    }
    @Test

    void testGetNotifications() {
        List<Notification> notificationList = new ArrayList<>(); // Mocked notification list
        when(notificationRepo.findAllByRecipientEmailOrRecipientUsername("testUser", "testUser"))
                .thenReturn(notificationList);

        List<Notification> notifications = notificationService.getNotifications();

        assertEquals(notificationList, notifications);
        verify(notificationRepo, times(1))
                .findAllByRecipientEmailOrRecipientUsername("testUser", "testUser");
    }
    @Test
    void testGetNotificationById() {
        List<Notification> notificationList = new ArrayList<>(); // Mocked notification list
        when(notificationRepo.findAllByRecipientEmailOrRecipientUsername("testUser", "testUser"))
                .thenReturn(notificationList);

        List<Notification> notifications = notificationService.getNotificationById();

        assertEquals(notificationList, notifications);
        verify(notificationRepo, times(1))
                .findAllByRecipientEmailOrRecipientUsername("testUser", "testUser");
    }

    @Test
    void testDeleteNotificationById() {
        Long id = 1L;

        doNothing().when(notificationRepo).deleteById(id);
        notificationService.deleteNotificationById(id);

        verify(notificationRepo, times(1)).deleteById(id);
    }
    @Test
    void testSetNotificationIsRead() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setIsRead(false);

        when(notificationRepo.getReferenceById(1L)).thenReturn(notification);
        when(notificationRepo.save(notification)).thenReturn(notification);

        notificationService.setNotificationIsRead(1L);

        assertEquals(true, notification.getIsRead());
        verify(notificationRepo, times(1)).getReferenceById(1L);
        verify(notificationRepo, times(1)).save(notification);
    }
}
