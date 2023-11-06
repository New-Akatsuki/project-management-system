package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class KanBanController {

    private final TaskService taskService;
    private final UserService userService;
    private final PhaseService phaseService;

    @GetMapping("/tasks/byPhase/{phaseId}")
    public ResponseEntity<List<Task>> getTasksByPhase(@PathVariable Long phaseId) {
        List<Task> tasks = taskService.getTasksByPhase(phaseId);
        if (tasks != null && !tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add-phase")
    public ResponseEntity<Phase> addPhase(@RequestBody Phase phase) {
        log.info("new phase: {}", phase);
        return ResponseEntity.ok(phaseService.createPhase(phase));
    }
    @GetMapping("/phases")
    public ResponseEntity<List<Phase>> getPhase() {
        List<Phase> phase = phaseService.getAllPhase();
        return ResponseEntity.ok(phase);
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<List<TaskViewObject>> getFakeGanttData() {
        List<TaskViewObject> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/users") // New endpoint to fetch users
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskViewObject> addTask(@RequestBody TaskFormInput task) {
        log.info("new task: {}", task);
        return ResponseEntity.ok(taskService.createTask(task));
    }


}
