package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        Department department = new Department();
        when(departmentRepository.save(department)).thenReturn(department);

        Department savedDepartment = departmentService.save(department);
        assertNotNull(savedDepartment);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testUpdateDepartment() {
        Department department = new Department();
        department.setId(1);
        department.setName("New Department");

        Department existingDepartment = new Department();
        existingDepartment.setId(1);
        existingDepartment.setName("Existing Department");

        when(departmentRepository.findById(1)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(existingDepartment)).thenReturn(existingDepartment);

        Department updatedDepartment = departmentService.updateDepartment(department);

        assertEquals("New Department", updatedDepartment.getName());
        assertTrue(updatedDepartment.isActive());
        verify(departmentRepository, times(1)).findById(1);
        verify(departmentRepository, times(1)).save(existingDepartment);
    }

     @Test
     void testUpdateDepartmentReturnNull() {
         Department department = new Department();
         department.setId(1);
         department.setName("New Department");

         Department existingDepartment = new Department();
         existingDepartment.setId(1);
         existingDepartment.setName("Existing Department");

         when(departmentRepository.findById(1)).thenReturn(Optional.empty());
         when(departmentRepository.save(existingDepartment)).thenReturn(existingDepartment);

         Department updatedDepartment = departmentService.updateDepartment(department);

         assertNull(updatedDepartment);
         verify(departmentRepository, times(1)).findById(1);
     }

    @Test
    void testGetDepartmentById() {
        Department department = new Department();
        department.setId(1);

        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        Department foundDepartment = departmentService.getDepartmentById(1);
        assertNotNull(foundDepartment);
        assertEquals(1, foundDepartment.getId());
        verify(departmentRepository, times(1)).findById(1);
    }

    @Test
    void testDelete() {
        Department department = new Department();
        department.setId(1);

        doNothing().when(departmentRepository).deleteById(1);
        departmentService.delete(department);

        verify(departmentRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetDepartmentByName() {
        String departmentName = "TestDepartment";
        Department department = new Department();
        department.setName(departmentName);

        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(department);

        Department foundDepartment = departmentService.getDepartmentByName(departmentName);
        assertNotNull(foundDepartment);
        assertEquals(departmentName, foundDepartment.getName());
        verify(departmentRepository, times(1)).findDepartmentByName(departmentName);
    }
   @Test
     void testGetAllDepartments_PM_Dh() {
         Department department = new Department();
         department.setId(1);

         when(userService.getCurrentUser()).thenReturn(createUser(Role.PM,department));

         Department referenceDepartment = new Department();
         referenceDepartment.setId(1);
         when(departmentRepository.getReferenceById(1)).thenReturn(referenceDepartment);

         List<Department> departments = departmentService.getAllDepartments();
         assertNotNull(departments);
            assertEquals(1, departments.size());
            assertEquals(1, departments.get(0).getId());
            verify(departmentRepository, times(1)).getReferenceById(1);
            verify(userService, times(1)).getCurrentUser();
        }



        @Test
        void testGetAllDepartments_Not_PM_Dh(){
        when(userService.getCurrentUser()).thenReturn(createUser(Role.MEMBER,null));
        List<Department> allDepartments = List.of(new Department(), new Department());
        when(departmentRepository.findAll()).thenReturn(allDepartments);
        List<Department> departments = departmentService.getAllDepartments();
        assertNotNull(departments);
        assertEquals(2, departments.size());
        verify(departmentRepository, times(1)).findAll();
       verify(userService, times(1)).getCurrentUser();
        }
        private User createUser(Role role, Department department) {
        User user = new User();
        user.setRole(role);
        user.setDepartment(department);

        return user;
        }



}
