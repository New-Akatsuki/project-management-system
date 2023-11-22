package org.blank.projectmanagementsystem.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.blank.projectmanagementsystem.domain.Enum.NotificationType;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.blank.projectmanagementsystem.service.QueueInfoService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationAspect {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username,username).orElse(null);
    }

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.ProjectServiceImpl.saveProject(..))",
            argNames = "result",
            returning = "result"
    )
    public void sendNotificationAfterProjectCreation(Project result) throws JsonProcessingException {
        if (result != null) {
            User user = getCurrentUser();
            Notification notification = Notification.builder()
                    .message("Project " + result.getName() + " has been created")
                    .date(LocalDateTime.now())
                    .link("/projects")
                    .type(NotificationType.PROJECT)
                    .projectId(result.getId())
                    .recipient(user)
                    .isRead(false)
                    .build();

            result.getContractMembers().forEach(contractMember -> {
                if (!contractMember.equals(user)) {
                    notification.setMessage("You have been assigned to project [" + result.getName() + "] as a contract member");
                    notification.setRecipient(contractMember);
                    notificationService.saveNotification(notification);
                    notificationService.sendNotification(notification, contractMember.getId());
                }
            });
            result.getFocMembers().forEach(focMember -> {
                if (!focMember.equals(user)) {
                    notification.setMessage("You have been assigned to project [" + result.getName() + "] as a foc member");
                    notification.setRecipient(focMember);
                    notificationService.saveNotification(notification);
                    notificationService.sendNotification(notification, focMember.getId());
                }
            });
        }
    }

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.IssueServiceImpl.createIssue(..))",
            argNames = "result",
            returning = "result"
    )
    public void sendNotificationAfterIssueCreation(Issue result) throws JsonProcessingException {
        if (result!=null){
            Notification notification = Notification.builder()
                    .message("You have been assigned to issue [" + result.getTitle()+ "] as a PIC")
                    .date(LocalDateTime.now())
                    .link("/issues/"+result.getId()+"/details")
                    .type(NotificationType.ISSUE)
                    .recipient(result.getPic())
                    .isRead(false)
                    .build();
            notificationService.saveNotification(notification);
            notificationService.sendNotification(notification, result.getPic().getId());
        }
    }

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.TaskServiceImpl.createTask(..))",
            argNames = "result",
            returning = "result"
    )
    public void sendNotificationAfterTaskCreation(TaskViewObject result) throws JsonProcessingException {
        if (result!=null){
            Notification notification = Notification.builder()
                    .message("You have been assigned to [" + result.getName()+ "] task as a assignee")
                    .date(LocalDateTime.now())
                    .link("/projects?projectId="+result.getProjectId())
                    .type(NotificationType.TASK)
                    .projectId(result.getProjectId())
                    .taskId(result.getId())
                    .isRead(false)
                    .build();

            result.getAssignees().forEach(assignee -> {
                notification.setRecipient(userRepository.getReferenceById(assignee.getId()));
                notificationService.saveNotification(notification);
                notificationService.sendNotification(notification, assignee.getId());
            });
        }
    }

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.TaskServiceImpl.updateTask(..))",
            argNames = "result",
            returning = "result"
    )
    public void sendNotificationAfterTaskCompleted(Task result) throws JsonProcessingException {
        if (result!=null){
            User user = getCurrentUser();
            if (result.isStatus()){
                Notification notification = Notification.builder()
                        .message("Task " + result.getName()+ " has been completed with "+result.getActualHours()+" hours by " + user.getName())
                        .date(LocalDateTime.now())
                        .link("/projects/"+result.getProject().getId()+"/workspace")
                        .type(NotificationType.TASK)
                        .recipient(result.getProject().getProjectManager())
                        .isRead(false)
                        .build();
                notificationService.saveNotification(notification);
                notificationService.sendNotification(notification, result.getProject().getProjectManager().getId());
            }
        }
    }

}
