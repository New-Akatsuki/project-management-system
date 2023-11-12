package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.repository.NotificationRepo;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepo.save(notification);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notification> getNotifications() {
        return notificationRepo.findAllByRecipientEmailOrRecipientUsername(getUsername(), getUsername());
    }
}
