package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhaseRepository phaseRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getAllTasks() {
        // Mocking the SecurityContext to return a dummy user
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.getAuthentication().setName("testUser");

        // Mocking the UserRepository to return a dummy user
        when(userRepository.findByUsernameOrEmail(any(), any()))
                .thenReturn(Optional.of(new User()));

        // Mocking the TaskRepository to return a list of tasks
        when(taskRepository.findAllByAssigneesId(any()))
                .thenReturn(Collections.singletonList(new Task(/* provide necessary task details */)));

        List<TaskViewObject> tasks = taskService.getAllTasks();

        // Assert the result based on the mocked data
        assertEquals(1, tasks.size());
        // Add more assertions based on your specific implementation
    }

}
