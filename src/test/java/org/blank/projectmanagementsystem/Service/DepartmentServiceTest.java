package org.blank.projectmanagementsystem.Service;

import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.blank.projectmanagementsystem.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    public void testSaveDepartment() {
        // Given
        Department departmentToSave = new Department(1, "Test Department", true);

        // When
        when(departmentRepository.save(departmentToSave)).thenReturn(departmentToSave);
        Department savedDepartment = departmentService.save(departmentToSave);

        // Then
        assertEquals(departmentToSave, savedDepartment);
    }
    @Test
    public void testGetDepartmentByName() {
        // Given
        String departmentName = "Test Department";
        Department department = new Department(1, departmentName, true);

        // When
        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(department);
        Department retrievedDepartment = departmentService.getDepartmentByName(departmentName);

        // Then
        assertEquals(department, retrievedDepartment);
    }

    @Test
    public void testFindDepartmentByName() {
        // Given
        String departmentName = "Test Department";
        Department department = new Department(1, departmentName, true);

        // When
        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(department);
        Department retrievedDepartment = departmentService.findDepartmentByName(departmentName);

        // Then
        assertEquals(department, retrievedDepartment);
    }
//for update show me


    @Test
    public void testUpdateDepartment() {
        // Arrange
        Department existingDepartment = new Department(1, "Existing Department", true);
        Department updatedDepartment = new Department(1, "Updated Department", true);

        // Mocking the behavior of the repository
        when(departmentRepository.findById(1)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(any())).thenReturn(updatedDepartment);

        // Act
        Department result = departmentService.updateDepartment(updatedDepartment);

        // Assert
        assertEquals("Updated Department", result.getName());
        // Add more assertions if needed
    }

    @Test
    public void testGetDepartmentById() {
        // Given
        Department department = new Department(1, "Test Department", true);

        // When
        when(departmentRepository.getReferenceById(department.getId())).thenReturn(department);
        Department retrievedDepartment = departmentService.getDepartmentById(department.getId());

        // Then
        assertEquals(department, retrievedDepartment);
    }

    @Test
    public void testDeleteDepartment() {
        // Given
        Department department = new Department(1, "Test Department", true);

        // When
        doNothing().when(departmentRepository).deleteById(department.getId());
        departmentService.delete(department);

        // Then
        verify(departmentRepository, times(1)).deleteById(department.getId());
    }



}
