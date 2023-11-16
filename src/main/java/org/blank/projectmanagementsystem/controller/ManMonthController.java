package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.TaskService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        public ResponseEntity<List<TaskViewObject>> getTaskData() {
                List<TaskViewObject> tasks = taskService.getAllTasks();
                return ResponseEntity.ok(tasks);
        }

        @GetMapping("/get-project-data")
        public ResponseEntity<List<ProjectViewObject>> getProject() {
                List<ProjectViewObject> projects = projectService.getAllProjects();
                return ResponseEntity.ok(projects);
        }

        @GetMapping("/get-department-data")
        public ResponseEntity<List<Department>> getDepartment() {
                List<Department> departments = departmentService.getAllDepartments();
                return ResponseEntity.ok(departments);
        }


        @GetMapping("/get-projects")
        public ResponseEntity<List<ProjectViewObject>> getProjectsByDepartment(@RequestParam Long departmentId) {
                List<ProjectViewObject> projects = projectService.getProjectsByDepartment(departmentId);
                return ResponseEntity.ok(projects);
        }

        @GetMapping("/get-tasks")
        public ResponseEntity<List<TaskViewObject>> getTasksByProject(@RequestParam Long projectId) {
                List<TaskViewObject> tasks = taskService.getTasksByProject(projectId);
                return ResponseEntity.ok(tasks);
        }

}
