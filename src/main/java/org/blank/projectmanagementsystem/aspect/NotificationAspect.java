package org.blank.projectmanagementsystem.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.blank.projectmanagementsystem.domain.Enum.NotificationType;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.blank.projectmanagementsystem.service.QueueInfoService;
import org.blank.projectmanagementsystem.service.TaskService;
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
    private final UserService userService;
    private final TaskService taskService;

    private boolean showNotificationForTaskComplete = true;

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.ProjectServiceImpl.saveProject(..))",
            argNames = "result",
            returning = "result"
    )
    public void sendNotificationAfterProjectCreation(Project result) throws JsonProcessingException {
        if (result != null) {
            User user = userService.getCurrentUser();
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
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.IssueServiceImpl.addSolutiontoIssue(..))",
            argNames = "result",
            returning = "result"
    )
    public void sendNotificationAfterIssueAlreadySolved(Issue result) throws JsonProcessingException {
        if (result!=null){
            User user = userService.getCurrentUser();
            Notification notification = Notification.builder()
                    .message("Issue " + result.getTitle()+ " has been filled with solutions by " + user.getName())
                    .date(LocalDateTime.now())
                    .link("/issues/"+result.getId()+"/details")
                    .type(NotificationType.TASK)
                    .recipient(result.getCreatedBy())
                    .isRead(false)
                    .build();
            notificationService.saveNotification(notification);
            notificationService.sendNotification(notification, result.getCreatedBy().getId());
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
                notification.setRecipient(userService.getUserById(assignee.getId()));
                notificationService.saveNotification(notification);
                notificationService.sendNotification(notification, assignee.getId());
            });
        }
    }


    @Before("execution(* org.blank.projectmanagementsystem.service.impl.TaskServiceImpl.updateTask(..))&&args(result)")
    public void decisionForNotificationBeforeTaskComplete(TaskFormInput result) throws JsonProcessingException {
        showNotificationForTaskComplete = result != null && result.isStatus() && !taskService.getTaskById(result.getId()).isStatus();
    }

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.TaskServiceImpl.updateTask(..))",
            argNames = "result",
            returning = "result"
    )
    public void sendNotificationAfterTaskCompleted(Task result) throws JsonProcessingException {
        if (result!=null){
            User user = userService.getCurrentUser();
            if (result.isStatus()&&showNotificationForTaskComplete){
                Notification notification = Notification.builder()
                        .message("Task " + result.getName()+ " has been completed with "+result.getActualHours()+" hours by " + user.getName())
                        .date(LocalDateTime.now())
                        .link("/projects/"+result.getProject().getId()+"/"+result.getProject().getName()+"/workspace")
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
