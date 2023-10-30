package org.blank.projectmanagementsystem.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.blank.projectmanagementsystem.service.QueueInfoService;
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
public class NotificationAspect {

    private final NotificationService notificationService;

    private final QueueInfoService queueInfoService;

    private final RabbitTemplate rabbitTemplate;

    private final DirectExchange directExchange;

//    @AfterReturning(
//            pointcut = "execution(* com.project.ecommerce.api.OrderApi.addOrder(..)) && args(orderDto)",
//            argNames = "result, orderDto",
//            returning = "result"
//    )
//    public void sendNotificationAfterOrderCreation(Object result, OrderDetailDto orderDto) throws JsonProcessingException {
//        if (result instanceof ResponseEntity) {
//            ResponseEntity<OrderDetailVo> responseEntity = (ResponseEntity<OrderDetailVo>) result;
//
//            // Check if the status code indicates success (e.g., 2xx range)
//            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                OrderDetailVo orderVo = responseEntity.getBody();
//                // Send a notification using the notificationService
//                assert orderVo != null;
//                sendNotification(orderVo.getOrderId(), "New Order Arrived!", getAdminRoutingKey());
//            }
//        }
//    }


    private String getAdminRoutingKey() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return queueInfoService.getRoutingKeyByUsername(username);
    }

    private void sendNotification(long itemId, String message, String routingKey) throws JsonProcessingException {
        // send notification to admin
        Map<String, Object> notification = new HashMap<>();
        notification.put("message", message);
        notification.put("orderId", itemId);

        // change map to json
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonNotification = objectMapper.writeValueAsString(notification);

        // save notification to database
        notificationService.saveNotification(Notification.builder().taskId(itemId).message(message).date(LocalDate.now()).build());

        // send notification to admins using topic exchange
        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, jsonNotification);
    }

}
