package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.mapper.TaskMapper;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.TaskService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
    private final PhaseRepository phaseRepository;
    private final UserService userService;

    private final TaskMapper taskMapper = new TaskMapper();


    @Override
    @Transactional(readOnly = true)
    public List<TaskViewObject> getAllTasks() {
        return taskRepository.findAllByAssigneesId(userService.getCurrentUser().getId())
                .stream().map(taskMapper::mapToTaskViewObject).toList();
    }

    @Override
    @Transactional
    public TaskViewObject createTask(TaskFormInput taskFormInput) {
        Task task = taskMapper.mapToTask(taskFormInput);
        Task resultTask = fillTaskData(taskFormInput, task);

        if(!isAuthorized(resultTask.getProject())){
            return null;
        }

        Task savedTask = taskRepository.save(resultTask);
        checkProjectStatus(savedTask.getProject());
        return taskMapper.mapToTaskViewObject(savedTask);
    }

    @Override
    @Transactional
    public Task updateTask(TaskFormInput taskFormInput) {
        Task task = taskMapper.mapToTask(taskFormInput);
        //Set phase and project if it exists
        task.setId(taskFormInput.getId());
        var modifyTask = fillTaskData(taskFormInput, task);

        if(!isAuthorized(modifyTask.getProject())){
            return null;
        }
        //reset subtask date
        resetSubTaskDate(modifyTask);
        var resultTask = taskRepository.save(modifyTask);
        if (modifyTask.isStatus()) {
            makeChildComplete(modifyTask);
            //check if siblings of the task are completed, then set the parent task to complete
            var siblings = taskRepository.findAllByParentTask(modifyTask.getParentTask());

            if (siblings.stream().allMatch(Task::isStatus)) {
                if (modifyTask.getParentTask() != null) {
                    modifyTask.getParentTask().setStatus(true);
                    modifyTask.getParentTask().setActualHours(siblings.stream().reduce(0f, (acc, val) -> acc + val.getActualHours(), Float::sum));
                    modifyTask.getParentTask().setActualDueDate(modifyTask.getActualDueDate());
                }
            }

        } else {
            makeChildIncomplete(modifyTask);
            //check if this task have parent and siblings, then set the parent task to incomplete

            var siblings = taskRepository.findAllByParentTask(modifyTask.getParentTask());
            if (siblings.stream().anyMatch(t -> !t.isStatus())) {
                if (modifyTask.getParentTask() != null) {
                    modifyTask.getParentTask().setStatus(false);
                    modifyTask.getParentTask().setActualHours(0f);
                    modifyTask.getParentTask().setActualDueDate(null);
                }
            }
        }
        checkProjectStatus(resultTask.getProject());
        return resultTask;
    }

    private void makeChildComplete(Task task) {
        AtomicReference<Float> parentTaskActualHours = new AtomicReference<>(task.getActualHours());
        var subTasks = taskRepository.findAllByParentTask(task);
        subTasks.stream().filter(Task::isStatus).forEach(val -> {
            parentTaskActualHours.updateAndGet(v -> v - val.getActualHours());
        });
        subTasks.stream().filter(t -> !t.isStatus()).forEach(val -> {
            val.setStatus(true);
            val.setActualHours(parentTaskActualHours.get() / subTasks.size());
            val.setActualDueDate(task.getActualDueDate());
            makeChildComplete(val);
        });
    }

    private void makeChildIncomplete(Task task) {
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
                            .map(userService::getUserById)
                            .collect(Collectors.toSet())
            );
        }
        return task;
    }

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
            if(isAuthorized(task.getProject())) {
                task.getAssignees().clear();
                clearAssignees(task);
                taskRepository.deleteById(id);
                checkProjectStatus(task.getProject());
            }
        });
    }

    @Override
    public List<TaskViewObject> getTasksByProject(Long projectId) {
        return taskRepository.findAllByProjectId(projectId).stream().map(taskMapper::mapToTaskViewObject).toList();
    }

    @Override
    public List<TaskViewObject> getMemberTaskByProject(Long projectId) {
        var user = userService.getCurrentUser();
        return taskRepository.findAllByProjectIdAndAssigneesContaining(projectId, user)
                .stream().map(taskMapper::mapToTaskViewObject).toList();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.getReferenceById(id);
    }


    private void clearAssignees(Task task) {
        task.getAssignees().clear();
        var subTasks = taskRepository.findAllByParentTask(task);
        subTasks.forEach(val -> {
            val.getAssignees().clear();
            clearAssignees(val);
        });
    }

    private void checkProjectStatus(Project project){
        var tasks = taskRepository.findAllByProjectId(project.getId());
        if(project.getStatus()!=ProjectStatus.PENDING){
            if(tasks.size()==0){
                project.setStatus(ProjectStatus.ONGOING);
            }else if(tasks.stream().allMatch(Task::isStatus)){
                project.setStatus(ProjectStatus.FINISHED);
            }else {
                project.setStatus(ProjectStatus.ONGOING);
            }
        }
    }

    private boolean isAuthorized(Project project){
        return project.getStatus()!=ProjectStatus.PENDING;
    }
}

