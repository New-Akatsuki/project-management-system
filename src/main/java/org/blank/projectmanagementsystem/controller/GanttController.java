package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.formInput.PhaseDto;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GanttController {
    private final TaskService taskService;
    private final PhaseService phaseService;
    private final ProjectService projectService;

    @GetMapping("/get-phase-data")
    public ResponseEntity<Map<String,Object>> getPhaseData(@RequestParam long projectId) {
        var phaseList =  phaseService.getPhases(projectId);
        var taskList = taskService.getAllTasks();
        var projectMembers = projectService.getProjectMembers(projectId);
        return ResponseEntity.ok(new HashMap<>(Map.of("phases", phaseList, "tasks", taskList, "projectMembers", projectMembers)));
    }

    @PostMapping("/add-phase")
    public ResponseEntity<PhaseDto> addPhase(@RequestBody PhaseDto phaseDto) {
        log.info("new phase: {}", phaseDto);
        return ResponseEntity.ok(phaseService.createPhase(phaseDto));

    }
    @PutMapping("/update-phase")
    public ResponseEntity<PhaseDto> updatePhase(@RequestBody PhaseDto phaseDto) {
        log.info("update phase: {}", phaseDto);
        return ResponseEntity.ok(phaseService.updatePhase(phaseDto));
    }

    @DeleteMapping("/delete-phase")
    public ResponseEntity<PhaseDto> deletePhase(@RequestBody PhaseDto phaseDto) {
        log.info("update phase: {}", phaseDto);
        phaseService.deletePhase(phaseDto.getId());
        return ResponseEntity.ok(phaseDto);
    }

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
//        return null;
    }

    @PutMapping("/update-task")
    public ResponseEntity<TaskViewObject> updateTask(@RequestBody TaskFormInput task) {
        log.info("update task: {}", task);
        return ResponseEntity.ok(taskService.updateTask(task));
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<TaskFormInput> deleteTask(@RequestBody TaskFormInput task) {
        log.info("delete task: {}", task);
        taskService.deleteTask(task.getId());
        //response json msg
        return ResponseEntity.ok(task);
    }

}
