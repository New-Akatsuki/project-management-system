package org.blank.projectmanagementsystem.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.blank.projectmanagementsystem.domain.entity.Notification;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getNotifications();

    void sendNotification(Notification notification, long id);

    List<Notification> getNotificationById(Long id);
}
