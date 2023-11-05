package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;

import java.util.List;

public interface TaskService {
    List<TaskViewObject> getAllTasks();
    TaskViewObject getTaskById(Long id);
    TaskViewObject createTask(TaskFormInput task);

    TaskViewObject updateTask(TaskFormInput task);
    void deleteTask(Long id);

}
