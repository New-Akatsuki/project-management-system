package org.blank.projectmanagementsystem.service;


import org.blank.projectmanagementsystem.domain.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification saveNotification(Notification notification);

    List<Notification> getNotifications();

    void sendNotification(Notification notification, long id);

    List<Notification> getNotificationById();

    void deleteNotificationById(Long id);
}
