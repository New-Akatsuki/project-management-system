package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.mapper.TaskMapper;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.TaskService;
import org.hibernate.annotations.OnDelete;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PhaseRepository phaseRepository;
    private final NotificationServiceImpl notificationService;

    private final TaskMapper taskMapper = new TaskMapper();

    private User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username,username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskViewObject> getAllTasks() {
        return taskRepository.findAllByAssignees_Id(getCurrentUser().getId())
                .stream().map(taskMapper::mapToTaskViewObject).toList();
    }

    @Override
    public List<TaskViewObject> getTasksByUser() {
        return taskRepository.findAllByAssignees_Id(getCurrentUser().getId())
                .stream().map(taskMapper::mapToTaskViewObject).toList();
    }

    @Override
    public TaskViewObject getTaskById(Long id) {
        return taskRepository.findById(id).map(taskMapper::mapToTaskViewObject).orElse(null);
    }

    @Override
    public TaskViewObject createTask(TaskFormInput taskFormInput) {
        Task task = taskMapper.mapToTask(taskFormInput);
        // Save the task to the database
        Task savedTask = taskRepository.save(fillTaskData(taskFormInput, task));
        // Map and return the saved task as a TaskViewObject
        return taskMapper.mapToTaskViewObject(savedTask);
    }


    @Override
    @Transactional
    public TaskViewObject updateTask(TaskFormInput taskFormInput) {
        Task task = taskMapper.mapToTask(taskFormInput);
        //Set phase and project if it exists
        task.setId(taskFormInput.getId());
        var modifyTask = fillTaskData(taskFormInput, task);
        //reset subtask date
        resetSubTaskDate(modifyTask);
        return taskMapper.mapToTaskViewObject(taskRepository.save(modifyTask));
    }

    private Task fillTaskData(TaskFormInput taskFormInput, Task task) {
        if (taskFormInput.getPhase() != null) {
            Phase phase = phaseRepository.getReferenceById(taskFormInput.getPhase());
            task.setPhase(phase);
            task.setProject(phase.getProject());
        }
        if (taskFormInput.getParent() != null) {
            Task parent = taskRepository.findById(taskFormInput.getParent()).orElse(null);
            task.setParentTask(parent);
        }
        if (taskFormInput.getAssignees() != null) {
            task.setAssignees(new HashSet<>());
            task.getAssignees().addAll(
                    taskFormInput.getAssignees().stream()
                            .map(id -> userRepository.findById(id).orElse(null))
                            .collect(Collectors.toSet())
            );
        }
        return task;
    }

    // recursive function that
    // reset start date and end date of subtasks as parent task's start date and end date
    // if subtask start date or end date is not in range of parent task
    private void resetSubTaskDate(Task task) {
        var subTasks = taskRepository.findAllByParentTask(task);
        subTasks.forEach(val -> {
            if (val.getStartDate().isBefore(task.getStartDate())) {
                val.setStartDate(task.getStartDate());
            }
            if (val.getDueDate().isAfter(task.getDueDate())) {
                val.setDueDate(task.getDueDate());
            }
            resetSubTaskDate(val);
        });
    }


    @Override
    @Transactional
    public void deleteTask(Long id) {
        //check task exist or not
        var currentTask = taskRepository.findById(id);
        currentTask.ifPresent(task -> {
            task.getAssignees().clear();
            clearAssignees(task);
        });

        taskRepository.deleteById(id);
    }


    //create recursive function to get all subtask and clear assignees
    private void clearAssignees(Task task) {
        task.getAssignees().clear();
        var subTasks = taskRepository.findAllByParentTask(task);
        subTasks.forEach(val -> {
            val.getAssignees().clear();
            clearAssignees(val);
        });
    }
}

