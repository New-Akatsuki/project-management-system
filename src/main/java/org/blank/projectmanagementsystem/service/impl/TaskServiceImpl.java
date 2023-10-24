package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.mapper.TaskMapper;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
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
        //add parent task if exist
        var taskParent = taskRepository.findById(taskFormInput.getParent());
        taskParent.ifPresent(task::setParentTask);

        //add assignees if exist
        if(task.getAssignees()==null){
            task.setAssignees(new HashSet<>());
        }
        task.getAssignees().addAll(taskFormInput.getAssignees().stream()
                .map(id->userRepository.findById(id)
                        .orElse(null)).toList());
        //add subtask if exist
        return taskMapper.mapToTaskViewObject(taskRepository.save(task));
    }

    @Override
    public TaskViewObject updateTask(TaskFormInput taskFormInput) {
        Task parent = taskRepository.findById(taskFormInput.getParent()).orElse(null);
        Task task = taskMapper.mapToTask(taskFormInput);
        task.setId(taskFormInput.getId());
        task.setParentTask(parent);
        return taskMapper.mapToTaskViewObject(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
