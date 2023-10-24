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
        log.info("========================================================");
        log.info("new task: {}", task);
        log.info("========================================================");

//        return ResponseEntity.ok(taskService.createTask(task));
        return ResponseEntity.ok(taskService.createTask(task));
    }


}
