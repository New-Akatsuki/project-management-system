package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PhaseRepository phaseRepository;

    private final TaskMapper taskMapper = new TaskMapper();

    private User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(username,username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskViewObject> getAllTasks() {
        return taskRepository.findAllByAssigneesId(getCurrentUser().getId())
                .stream().map(taskMapper::mapToTaskViewObject).toList();
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
    public Task updateTask(TaskFormInput taskFormInput) {
        Task task = taskMapper.mapToTask(taskFormInput);
        //Set phase and project if it exists
        task.setId(taskFormInput.getId());
        var modifyTask = fillTaskData(taskFormInput, task);
        var resultTask = taskRepository.save(modifyTask);
        if(modifyTask.isStatus()){
            makeChildComplete(modifyTask);
            //check if siblings of the task are completed, then set the parent task to complete
            if (modifyTask.getParentTask() != null) {
                var siblings = taskRepository.findAllByParentTask(modifyTask.getParentTask());
                if (siblings.stream().allMatch(Task::isStatus)) {
                    modifyTask.getParentTask().setStatus(true);
                    modifyTask.getParentTask().setActualHours(siblings.stream().reduce(0f, (acc, val) -> acc + val.getActualHours(), Float::sum));
                    modifyTask.getParentTask().setActualDueDate(modifyTask.getActualDueDate());
                }
            }
        }else {
            makeChildIncomplete(modifyTask);
            //check if this task have parent and siblings, then set the parent task to incomplete
            if (modifyTask.getParentTask() != null) {
                var siblings = taskRepository.findAllByParentTask(modifyTask.getParentTask());
                if (siblings.stream().anyMatch(t->!t.isStatus())) {
                    modifyTask.getParentTask().setStatus(false);
                    modifyTask.getParentTask().setActualHours(0f);
                    modifyTask.getParentTask().setActualDueDate(null);
                }
            }
        }
        //reset subtask date
        resetSubTaskDate(modifyTask);
        return resultTask;
    }

    private void makeChildComplete(Task task){
        AtomicReference<Float> parentTaskActualHours = new AtomicReference<>(task.getActualHours());
        var subTasks = taskRepository.findAllByParentTask(task);
        subTasks.stream().filter(Task::isStatus).forEach(val -> {
            parentTaskActualHours.updateAndGet(v -> v - val.getActualHours());
        });
        subTasks.stream().filter(t->!t.isStatus()).forEach(val -> {
            val.setStatus(true);
            val.setActualHours(parentTaskActualHours.get()/subTasks.size());
            val.setActualDueDate(task.getActualDueDate());
            makeChildComplete(val);
        });
    }

    private void makeChildIncomplete(Task task){
        var subTasks = taskRepository.findAllByParentTask(task);
        subTasks.forEach(val -> {
            val.setStatus(false);
            val.setActualHours(0f);
            val.setActualDueDate(null);
            makeChildIncomplete(val);
        });
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

    @Override
    public List<TaskViewObject> getTasksByProject(Long projectId) {
        return taskRepository.findAllByProjectId(projectId).stream().map(taskMapper::mapToTaskViewObject).toList();
    }

    @Override
    public List<TaskViewObject> getMemberTaskByProject(Long projectId) {
        var user = getCurrentUser();
        return taskRepository.findAllByProjectIdAndAssigneesContaining(projectId, user)
                .stream().map(taskMapper::mapToTaskViewObject).toList();
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

