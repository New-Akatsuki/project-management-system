package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;

import java.util.List;

public interface TaskService {
    List<TaskViewObject> getAllTasks();
    List<TaskViewObject> getTasksByUser();
    TaskViewObject getTaskById(Long id);
    TaskViewObject createTask(TaskFormInput task);
    Task updateTask(TaskFormInput task);
    void deleteTask(Long id);
}
