package org.blank.projectmanagementsystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pusher.rest.Pusher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Override
    public void sendNotification(Notification notification, long id) {

        String appId = "1682457";
        String key = "45b9b41cab6ad01f6264";
        String secret = "5aba9676c16d8ef8500f";
        String cluster = "ap1";

        try (Pusher pusher = new Pusher(appId, key, secret)) {


            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String notiJson = mapper.writeValueAsString(notification);

            log.info("In try statement");

            pusher.setCluster(cluster);
            pusher.setEncrypted(true);

            pusher.trigger("my-channel-" + id, "noti-event", notiJson);

            log.info("Pusher is triggered");

        } catch (Exception e) {
            log.error("Error while sending notification: {}", e.getMessage());
            log.error("Stack Trace: ", e);
            log.error("Cause: {}", e.getCause());
            log.error("Filled Stack Trace: ", e.fillInStackTrace());
        }


    }

    @Override
    public List<Notification> getNotificationById(Long id) {
        return notificationRepo.findAllByRecipientEmailOrRecipientUsername(getUsername(), getUsername());
    }
}
