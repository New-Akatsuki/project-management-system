package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.Priority;
import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.Enum.TaskGroup;
import org.blank.projectmanagementsystem.domain.Enum.TaskType;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.mapper.TaskMapper;
import org.blank.projectmanagementsystem.mapper.UserMapper;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private PhaseRepository phaseRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testGetAllTasks() {
        Task t = Task.builder()
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .priority(Priority.HIGH)
                .phase(Phase.builder().id(1L).build())
                .group(TaskGroup.A)
                .type(TaskType.TASK)

                .build();
        // Mock the behavior of userService.getCurrentUser() to return a user with a specific ID
        when(userService.getCurrentUser()).thenReturn(User.builder().id(1L).build());

        // Mock the behavior of taskRepository.findAllByAssigneesId() to return a list of tasks
        when(taskRepository.findAllByAssigneesId(anyLong())).thenReturn(List.of(t));

        // Call the method under test
        List<TaskViewObject> result = taskService.getAllTasks();

        assertEquals(1,result.size());
    }

    @Test
    void testCreateTaskWhenProjectOngoing() {
        Task t = Task.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .priority(Priority.HIGH)
                .phase(Phase.builder().id(1L).project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build()).build())
                .group(TaskGroup.A)
                .type(TaskType.TASK)
                .project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build())
                .build();

        TaskFormInput taskFormInput = TaskFormInput.builder()
                .id(1L)
                .priority("high")
                .type("task")
                .phase(1L)
                .build();
//        when(taskMapper.mapToTask(any())).thenReturn(t);
        when(phaseRepository.getReferenceById(any())).thenReturn(Phase.builder().id(1L).project(Project.builder().status(ProjectStatus.ONGOING).build()).build());
        when(taskRepository.save(any())).thenReturn(t);


        TaskViewObject result = taskService.createTask(taskFormInput);
        assertNotNull(result);
        verify(taskRepository, times(1)).save(any());
    }
    @Test
    void testCreateTaskWhenProjectPending() {
        Task t = Task.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .priority(Priority.HIGH)
                .phase(Phase.builder().id(1L).project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build()).build())
                .group(TaskGroup.A)
                .type(TaskType.TASK)
                .parentTask(Task.builder().id(1L).build())
                .project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build())
                .build();

        TaskFormInput taskFormInput = TaskFormInput.builder()
                .id(1L)
                .priority("high")
                .type("task")
                .phase(1L)
                .build();
//        when(taskMapper.mapToTask(any())).thenReturn(t);
        when(phaseRepository.getReferenceById(any())).thenReturn(Phase.builder().id(1L).project(Project.builder().status(ProjectStatus.PENDING).build()).build());


        TaskViewObject result = taskService.createTask(taskFormInput);
        assertNull(result);
        verify(taskRepository, times(0)).save(any());
    }

    @Test
    void testUpdateTask() {
        // Mocking data
        Task t = Task.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .priority(Priority.HIGH)
                .phase(Phase.builder().id(1L).project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build()).build())
                .group(TaskGroup.A)
                .type(TaskType.TASK)
                .parentTask(Task.builder()
                        .id(1L)
                        .status(false)
                        .actualDueDate(LocalDate.now())
                        .actualHours(0.0f)
                        .build()
                )
                .project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build())
                .status(true)
                .build();

        TaskFormInput taskFormInput = TaskFormInput.builder()
                .id(1L)
                .priority("high")
                .type("task")
                .phase(1L)
                .status(true)
                .parent(1L)
                .build();

        // Mocking behavior
        when(phaseRepository.getReferenceById(any())).thenReturn(Phase.builder().id(1L).project(Project.builder().status(ProjectStatus.ONGOING).build()).build());

        when(taskRepository.save(any(Task.class))).thenReturn(t);
        // Mock any other dependencies or behaviors as needed

        // Call the method to be tested
        Task resultTask = taskService.updateTask(taskFormInput);

        // Assertions
        assertNotNull(resultTask);
        verify(taskRepository, times(1)).save(any(Task.class));
    }
    @Test
    void testUpdateTaskReturnNull() {
        // Mocking data
        Task tp = Task.builder()
                .id(2L)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .priority(Priority.HIGH)
                .phase(Phase.builder().id(1L).project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build()).build())
                .group(TaskGroup.A)
                .type(TaskType.TASK)
                .project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build())
                .build();
        Task t = Task.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .priority(Priority.HIGH)
                .phase(Phase.builder().id(1L).project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build()).build())
                .group(TaskGroup.A)
                .type(TaskType.TASK)
                .parentTask(tp)
                .project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build())
                .build();

        TaskFormInput taskFormInput = TaskFormInput.builder()
                .id(1L)
                .priority("high")
                .type("task")
                .phase(1L)
                .build();

        // Mocking behavior
        when(phaseRepository.getReferenceById(any())).thenReturn(Phase.builder().id(1L).project(Project.builder().status(ProjectStatus.PENDING).build()).build());

        // Mock any other dependencies or behaviors as needed

        // Call the method to be tested
        Task resultTask = taskService.updateTask(taskFormInput);

        // Assertions
        assertNull(resultTask);

    }

    @Test
    void testUpdateTaskResetDateIfnotIntersetParentDate() {
        // Mocking data
        Task t = Task.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .priority(Priority.HIGH)
                .phase(Phase.builder().id(1L).project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build()).build())
                .group(TaskGroup.A)
                .type(TaskType.TASK)
                .parentTask(Task.builder()
                        .id(1L)
                        .status(false)
                        .actualDueDate(LocalDate.now())
                        .actualHours(0.0f)
                        .build()
                )
                .project(Project.builder().id(1L).status(ProjectStatus.ONGOING).build())
                .status(true)
                .build();

        TaskFormInput taskFormInput = TaskFormInput.builder()
                .id(1L)
                .priority("high")
                .type("task")
                .phase(1L)
                .start_date(LocalDate.now())
                .end_date(LocalDate.now())
                .status(true)
                .parent(1L)
                .build();

        // Mocking behavior
        when(phaseRepository.getReferenceById(any())).thenReturn(Phase.builder().id(1L).project(Project.builder().status(ProjectStatus.ONGOING).build()).build());

        when(taskRepository.findAllByParentTask(t)).thenReturn(List.of(t));
        when(taskRepository.save(any(Task.class))).thenReturn(t);
        // Mock any other dependencies or behaviors as needed

        // Call the method to be tested
        Task resultTask = taskService.updateTask(taskFormInput);

        // Assertions
        assertNotNull(resultTask);
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
