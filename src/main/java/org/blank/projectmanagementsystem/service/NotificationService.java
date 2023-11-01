package org.blank.projectmanagementsystem.service;


import org.blank.projectmanagementsystem.domain.entity.Notification;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getNotificationByUsername(String username);
}
