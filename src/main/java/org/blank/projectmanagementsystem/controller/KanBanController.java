package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KanBanController {

    private final TaskService taskService;
    private final UserService userService;
    private final PhaseService phaseService;

    @PostMapping("/api/add-phase")
    public ResponseEntity<Phase> addPhase(@RequestBody Phase phase) {
        log.info("new phase: {}", phase);
        return ResponseEntity.ok(phaseService.createPhase(phase));
    }
    @GetMapping("/api/phases")
    public ResponseEntity<List<Phase>> getPhase() {
        List<Phase> phase = phaseService.getAllPhase();
        return ResponseEntity.ok(phase);
    }

    @GetMapping("/api/all-tasks")
    public ResponseEntity<List<TaskViewObject>> getFakeGanttData() {
        List<TaskViewObject> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/api/users") // New endpoint to fetch users
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<TaskViewObject> addTask(@RequestBody TaskFormInput task) {
        log.info("new task: {}", task);
        return ResponseEntity.ok(taskService.createTask(task));
    }


}
