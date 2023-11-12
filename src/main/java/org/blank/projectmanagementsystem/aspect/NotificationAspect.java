package org.blank.projectmanagementsystem.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.blank.projectmanagementsystem.service.QueueInfoService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationAspect {

    private final NotificationService notificationService;

    private final QueueInfoService queueInfoService;

    private final RabbitTemplate rabbitTemplate;

    private final DirectExchange directExchange;

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.UserServiceImpl.save(..)) && args(user)",
            argNames = "user, saveUser",
            returning = "saveUser"
    )
    public void sendNotificationAfterOrderCreation(User user, User saveUser) throws JsonProcessingException {
        // Check if the user is not null
        log.info("user from aop: {}", user);
        if (user != null) {
            // Send a notification using the notificationService
            sendNotification(saveUser.getId(), String.format("New User (%s) is arrived!",saveUser.getName()), getUserRoutingKey(saveUser.getId()),saveUser);
        }
    }


    private String getUserRoutingKey(Long id) {
        return queueInfoService.getRoutingKeyById(id);
    }

    private void sendNotification(long taskId, String message, String routingKey, User user) throws JsonProcessingException {
        // send notification to admin
        Map<String, Object> notification = new HashMap<>();
        notification.put("message", message);
        notification.put("userId", taskId);

        // change map to json
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonNotification = objectMapper.writeValueAsString(notification);

        // save notification to database
        notificationService.saveNotification(Notification.builder().taskId(taskId).message(message).recipient(user).date(LocalDate.now()).build());

        log.info("Routing key: {}", routingKey);

        try {
            // send notification to admins using topic exchange
            rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, jsonNotification);

            log.info("Notification sent to the admin: {}", jsonNotification);
        } catch (AmqpException e) {
            log.error("Error sending notification to RabbitMQ: {}", e.getMessage());
        }
    }


}
