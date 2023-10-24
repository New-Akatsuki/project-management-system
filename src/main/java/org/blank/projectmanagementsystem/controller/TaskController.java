package org.blank.projectmanagementsystem.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskViewObject> addTask(@RequestBody TaskFormInput task) {
        log.info("new task: {}", task);

//        return ResponseEntity.ok(taskService.createTask(task));
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @Data
    @AllArgsConstructor
    static class TaskData{
        private String name;
        private String description;
        private String priority;
        private String start_date;
        private String end_date;
        private String plan_hours;
        private String actual_hours;
        private String actual_due_date;
        private String status;
        private String group;
        private String type;
    }

}
