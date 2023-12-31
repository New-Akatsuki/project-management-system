package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DepartmentAPI {
    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @PostMapping("/department-create")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.save(department);
        return ResponseEntity.ok(newDepartment);
    }

    @PutMapping("/department-edit/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department updatedDepartment) {
        Department existingDepartment = departmentService.getDepartmentById(Math.toIntExact(departmentId));

        if (existingDepartment != null) {
            // Update the existing department with the new information
            existingDepartment.setName(updatedDepartment.getName());
            // Update other fields as needed

            Department savedDepartment = departmentService.save(existingDepartment);
            return ResponseEntity.ok(savedDepartment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/department-toggle/{departmentId}")
    public ResponseEntity<String> toggleDepartmentStatus(@PathVariable("departmentId") Long departmentId) {
        Department department = departmentService.getDepartmentById(Math.toIntExact(departmentId));
        if (department != null) {
            department.setActive(!department.isActive());
            departmentService.save(department);
            return ResponseEntity.ok("Department status changed successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/department/status/{id}")
    public ResponseEntity<Department> updateMemberStatus(@PathVariable Integer id, @RequestParam boolean newStatus) {
        Department department = departmentService.getDepartmentById(id); // Change this to retrieve a specific user by id
        if (department != null) {
            department.setActive(newStatus);
            Department departmentUpdateStatus = departmentService.save(department); // Change Client to User since it seems like a typo
            return ResponseEntity.ok(department);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
