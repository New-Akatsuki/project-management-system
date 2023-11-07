package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }
    @Override
    public Department updateDepartment(Department department) {
        //check if department exists
        Department existingDepartment = departmentRepository.findById(department.getId()).orElse(null);
        if (existingDepartment == null) {
            log.error("Department with id {} not found", department.getId());
            return null;
        }
        existingDepartment.setName(department.getName());
        existingDepartment.setActive(true);
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public Department getDepartmentById(Department department) {
        return departmentRepository.findById(department.getId()).orElse(null);
    }

    @Override
    public void delete(Department department) {
        departmentRepository.deleteById(department.getId());
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
