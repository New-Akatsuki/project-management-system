package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.blank.projectmanagementsystem.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void testFindAllByParentTask() {
        // Given
        Task parentTask = new Task();
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);

        // When
        when(taskRepository.findAllByParentTask(parentTask)).thenReturn(tasks);

        // Then
        List<Task> foundTasks = taskService.findAllByParentTask(parentTask);
        assertThat(foundTasks).isEqualTo(tasks);
    }



    @Test
    public void testFindAllByProjectId() {
        // Given
        Long projectId = 1L;
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);

        // When
        when(taskRepository.findAllByProjectId(projectId)).thenReturn(tasks);

        // Then
        List<Task> foundTasks = taskService.findAllByProjectId(projectId);
        assertThat(foundTasks).isEqualTo(tasks);
    }

    @Test
    public void testFindAllByProjectIdAndAssigneesContaining() {
        // Given
        Long projectId = 1L;
        User assignee = new User();
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);

        // When
        when(taskRepository.findAllByProjectIdAndAssigneesContaining(projectId, assignee)).thenReturn(tasks);

        // Then
        List<Task> foundTasks = taskService.findAllByProjectIdAndAssigneesContaining(projectId, assignee);
        assertThat(foundTasks).isEqualTo(tasks);
    }

    @Test
    public void testFindAllByAssigneesId() {
        // Given
        Long assigneeId = 1L;
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);

        // When
        when(taskRepository.findAllByAssigneesId(assigneeId)).thenReturn(tasks);

        // Then
        List<Task> foundTasks = taskService.findAllByAssigneesId(assigneeId);
        assertThat(foundTasks).isEqualTo(tasks);
    }

    // Add more test cases as needed based on your repository methods
}

