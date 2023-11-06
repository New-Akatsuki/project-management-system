package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department save(Department department);

    List<Department> getAllDepartments();

}
