package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationAPI {
    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotification() {
        return ResponseEntity.ok(notificationService.getNotifications());
    }

    @PostMapping("/delete-notifications")
    public String deleteNotifications(@RequestBody List<Long> notificationIds) {
        // Implement your logic to delete notifications by their IDs
        // This is a simplified example, replace it with your actual deletion logic
        for (Long id : notificationIds) {
            notificationService.deleteNotificationById(id);
        }
        // Return a success message or any relevant response
        return "Notifications deleted successfully";
    }
}
