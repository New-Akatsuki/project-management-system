package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department updateDepartment(Department department);
    Department getDepartmentById(Integer id);
    Department save(Department department);
    void delete(Department department);

    Department getDepartmentByName(String departmentName);

    Department findDepartmentByName(String departmentName);
}
