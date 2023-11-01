package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.mapper.TaskMapper;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PhaseRepository phaseRepository;
    private final TaskMapper taskMapper = new TaskMapper();

    @Override
    @Transactional(readOnly = true)
    public List<TaskViewObject> getAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::mapToTaskViewObject).toList();
    }

    @Override
    public TaskViewObject getTaskById(Long id) {
        return taskRepository.findById(id).map(taskMapper::mapToTaskViewObject).orElse(null);
    }

    @Override
    public TaskViewObject createTask(TaskFormInput taskFormInput) {
        Task task = taskMapper.mapToTask(taskFormInput);
        //Set phase and project if it exists
        if (taskFormInput.getPhase() != null) {
            Phase phase = phaseRepository.getReferenceById(taskFormInput.getPhase());
            task.setPhase(phase);
            task.setProject(phase.getProject());
        }

        // Set the parent task if it exists
        if (taskFormInput.getParent() != null) {
            Task parentTask = taskRepository.findById(taskFormInput.getParent()).orElse(null);
            task.setParentTask(parentTask);
        }

        // Add assignees if they exist
        if (taskFormInput.getAssignees() != null) {
            task.setAssignees(new HashSet<>());
            task.getAssignees().addAll(
                    taskFormInput.getAssignees().stream()
                            .map(id -> userRepository.findById(id).orElse(null))
                            .collect(Collectors.toSet())
            );
        }

        // Save the task to the database
        Task savedTask = taskRepository.save(task);

        // Map and return the saved task as a TaskViewObject
        return taskMapper.mapToTaskViewObject(savedTask);
    }


    @Override
    public TaskViewObject updateTask(TaskFormInput taskFormInput) {
        Task task = taskMapper.mapToTask(taskFormInput);
        //Set phase and project if it exists
        if (taskFormInput.getPhase() != null) {
            Phase phase = phaseRepository.getReferenceById(taskFormInput.getPhase());
            task.setPhase(phase);
            task.setProject(phase.getProject());
        }
        task.setId(taskFormInput.getId());
        if(taskFormInput.getParent()!=null){
            Task parent = taskRepository.findById(taskFormInput.getParent()).orElse(null);
            task.setParentTask(parent);
        }
        return taskMapper.mapToTaskViewObject(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
