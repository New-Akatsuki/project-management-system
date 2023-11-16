package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PMORestController {
    private final DepartmentService departmentService;



    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @PreAuthorize("hasAnyAuthority('PMO')")
    @PostMapping("/department-create")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.save(department);
        return ResponseEntity.ok(newDepartment);
    }
    @PreAuthorize("hasAnyAuthority('PMO')")
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

}



