package org.blank.projectmanagementsystem.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.service.MailService;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MailAspect {
    private final MailService mailService;

    @AfterReturning(
            pointcut = "execution(* org.blank.projectmanagementsystem.service.impl.UserServiceImpl.createMember(..)) && args(formInput,defaultPassword)",
            argNames = "result,formInput,defaultPassword",
            returning = "result"
    )
    public void sendNotificationAfterProjectCreation(User result, AddUserFormInput formInput, String defaultPassword) throws JsonProcessingException {
        if (result != null) {
            mailService.sendDefaultPassword(result, defaultPassword);
            log.info("Default password sent to user {}\n\n\n\n", defaultPassword);
        }
    }
}
