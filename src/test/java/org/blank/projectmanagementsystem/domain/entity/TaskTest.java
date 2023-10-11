package org.blank.projectmanagementsystem.domain.entity;

import org.blank.projectmanagementsystem.domain.Enum.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;
    private Project project;
    private User user;

    @BeforeEach
    public void setUp() {
        project = new Project(); // Initialize Project object appropriately
        user = new User(); // Initialize User object appropriately

        task = Task.builder()
                .id(1)
                .name("Junit Task")
                .description("Junit Description")
                .priority(Priority.HIGH)
                .startDate(new Date())
                .dueDate(new Date())
                .planHours(10.5f)
                .actualStartDate(new Date())
                .actualDueDate(new Date())
                .actualHours(8.5f)
                .project(project)
                .assignees(Set.of(user))
                .parentTask(null) // Set appropriately if there is a parent task
                .subtasks(new HashSet<>()) // Initialize as needed
                .build();
    }

    @Test
    public void testTaskAttributes() {
        assertEquals(1, task.getId());
        assertEquals("Junit Task", task.getName());
        assertEquals("Junit Description", task.getDescription());
        assertEquals(Priority.HIGH, task.getPriority());
        assertNotNull(task.getStartDate());
        assertNotNull(task.getDueDate());
        assertEquals(10.5f, task.getPlanHours());
        assertNotNull(task.getActualStartDate());
        assertNotNull(task.getActualDueDate());
        assertEquals(8.5f, task.getActualHours());
        assertEquals(project, task.getProject());
        assertTrue(task.getAssignees().contains(user));
        assertNull(task.getParentTask()); // Adjust if there is a parent task
        assertNotNull(task.getSubtasks());
        assertEquals(0, task.getSubtasks().size());
    }

    @Test
    public void testEquals() {
        Task sameTask = Task.builder()
                .id(1)
                .name("Junit Task")
                .description("Junit Description")
                .priority(Priority.HIGH)
                .startDate(new Date())
                .dueDate(new Date())
                .planHours(10.5f)
                .actualStartDate(new Date())
                .actualDueDate(new Date())
                .actualHours(8.5f)
                .project(project)
                .assignees(Set.of(user))
                .parentTask(null)
                .subtasks(new HashSet<>())
                .build();

        assertEquals(task, sameTask);
    }

    @Test
    public void testNotEquals() {
        Task differentTask = Task.builder()
                .id(2)
                .name("Different Task")
                .description("Different Description")
                .priority(Priority.LOW)
                .startDate(new Date())
                .dueDate(new Date())
                .planHours(5.5f)
                .actualStartDate(new Date())
                .actualDueDate(new Date())
                .actualHours(3.5f)
                .project(new Project()) // Initialize a different Project object
                .assignees(new HashSet<>()) // Initialize a different Set of User objects
                .parentTask(null)
                .subtasks(new HashSet<>())
                .build();

        assertNotEquals(task, differentTask);
    }

    @Test
    public void testHashCode() {
        Task sameTask = Task.builder()
                .id(1)
                .name("Sample Task")
                .description("Task Description")
                .priority(Priority.HIGH)
                .startDate(new Date())
                .dueDate(new Date())
                .planHours(10.5f)
                .actualStartDate(new Date())
                .actualDueDate(new Date())
                .actualHours(8.5f)
                .project(project)
                .assignees(Set.of(user))
                .parentTask(null)
                .subtasks(new HashSet<>())
                .build();

        assertEquals(task.hashCode(), sameTask.hashCode());
    }
}

