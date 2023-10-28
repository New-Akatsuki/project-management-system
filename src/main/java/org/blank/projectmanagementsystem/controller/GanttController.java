package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GanttController {
    private final TaskService taskService;
    @GetMapping("/get-task-data")
    public ResponseEntity<List<TaskViewObject>> getFakeGanttData() {
        List<TaskViewObject> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    //for add Task
    @PostMapping("/add-task")
    public ResponseEntity<TaskViewObject> addTask(@RequestBody TaskFormInput task) {
        log.info("new task: {}", task);
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @PutMapping("/update-task")
    public ResponseEntity<TaskViewObject> updateTask(@RequestBody TaskFormInput task) {
        log.info("update task: {}", task);
        return ResponseEntity.ok(taskService.updateTask(task));
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<String> deleteTask(@RequestBody TaskFormInput task) {
        log.info("delete task: {}", task);
        taskService.deleteTask(task.getId());
        //response json msg
        return ResponseEntity.ok("delete success");
    }

}
