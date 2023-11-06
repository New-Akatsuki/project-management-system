package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department updateDepartment(Department department);
    Department getDepartmentById(Department department);
    Department save(Department department);
    void delete(Department department);
}
