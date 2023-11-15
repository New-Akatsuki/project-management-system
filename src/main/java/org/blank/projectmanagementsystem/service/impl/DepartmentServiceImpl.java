package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserService userService;
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
    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }



    @Override
    public void delete(Department department) {
        departmentRepository.deleteById(department.getId());
    }

    @Override
    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.findDepartmentByName(departmentName);
    }

    @Override
    public List<Department> getAllDepartments() {
        var currentUser = userService.getCurrentUser();
        if(currentUser.getRole()== Role.PM||currentUser.getRole()==Role.DH){
            return List.of(departmentRepository.getReferenceById(currentUser.getDepartment().getId()));
        }
        return departmentRepository.findAll();
    }
}
