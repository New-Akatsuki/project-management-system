package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ManMonthController {
        private final UserService userService;
        private final TaskService taskService;
        private final ProjectService projectService;
        private final DepartmentService departmentService;


        @GetMapping("/get-task-data")
        public ResponseEntity<List<TaskViewObject>> getFakeGanttData() {
                List<TaskViewObject> tasks = taskService.getAllTasks();
                return ResponseEntity.ok(tasks);
        }

    
}
