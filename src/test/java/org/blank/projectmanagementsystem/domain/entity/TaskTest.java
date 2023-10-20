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
    private Task taskWithBuilder;

    @BeforeEach
    public void setUp() {
        // Initialize the task object using setter methods
        task = new Task();
        task.setId(1);
        task.setName("Testing Task");
        task.setDescription("Testing Description:");
        task.setPriority(Priority.HIGH);
        task.setStartDate(new Date());
        task.setDueDate(new Date());
        task.setParentTask(null);
        task.setPlanHours(12.4f);
        task.setActualDueDate(new Date());
        task.setActualHours(10.5f);
        task.setSubtasks(new HashSet<>());

        // Initialize the task object using the builder pattern
        taskWithBuilder = Task.builder()
                .id(2)
                .name("Junit Task")
                .description("Junit Description")
                .priority(Priority.HIGH)
                .startDate(new Date())
                .dueDate(new Date())
                .planHours(10.5f)
                .actualDueDate(new Date())
                .actualHours(8.5f)
                .parentTask(null)
                .subtasks(new HashSet<>())
                .build();
    }

    @Test
    public void testGetId() {
        assertEquals(1L, task.getId());
        assertEquals(2L, taskWithBuilder.getId());
    }
    @Test
public void testGetName() {
        assertEquals("Testing Task", task.getName());
        assertEquals("Junit Task", taskWithBuilder.getName());
    }
    @Test
public void testGetDescription() {
        assertEquals("Testing Description:", task.getDescription());
        assertEquals("Junit Description", taskWithBuilder.getDescription());
    }
    @Test
public void testGetPriority() {
        assertEquals(Priority.HIGH, task.getPriority());
        assertEquals(Priority.HIGH, taskWithBuilder.getPriority());
    }


    @Test
public void testGetPlanHours() {
        assertEquals(12.4f, task.getPlanHours());
        assertEquals(10.5f, taskWithBuilder.getPlanHours());
    }

    @Test
public void testGetActualDueDate() {
        assertEquals(new Date(), task.getActualDueDate());
        assertEquals(new Date(), taskWithBuilder.getActualDueDate());
    }
    @Test
public void testGetActualHours() {
        assertEquals(10.5f, task.getActualHours());
        assertEquals(8.5f, taskWithBuilder.getActualHours());
    }
    @Test
public void testGetParentTask() {
        assertEquals(null, task.getParentTask());
        assertEquals(null, taskWithBuilder.getParentTask());
    }

    @Test
    public void testEquals()
{
        Task anotherTask = new Task();
        anotherTask.setId(1);
        anotherTask.setName("Testing Task");
        anotherTask.setDescription("Testing Description:");
        anotherTask.setPriority(Priority.HIGH);
        anotherTask.setStartDate(new Date());
        anotherTask.setDueDate(new Date());
        anotherTask.setParentTask(null);
        anotherTask.setPlanHours(12.4f);
        anotherTask.setActualDueDate(new Date());
        anotherTask.setActualHours(10.5f);
        anotherTask.setSubtasks(new HashSet<>());

        Task anotherTaskWithBuilder = Task.builder()
                .id(2)
                .name("Junit Task")
                .description("Junit Description")
                .priority(Priority.HIGH)
                .startDate(new Date())
                .dueDate(new Date())
                .planHours(10.5f)
                .actualDueDate(new Date())
                .actualHours(8.5f)
                .parentTask(null)
                .subtasks(new HashSet<>())
                .build();

        assertEquals(task, anotherTask);
        assertEquals(taskWithBuilder, anotherTaskWithBuilder);
    }




    // Additional test methods for other attributes...


}
