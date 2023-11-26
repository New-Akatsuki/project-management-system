package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentRepositoryTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testDepartmentConstructor() {
        // Given
        Integer id = 1;
        String name = "Test Department";

        // When
        Department department = new Department(id, name, true);

        // Then
        assertEquals(id, department.getId());
        assertEquals(name, department.getName());
        assertTrue(department.isActive());
    }

    @Test
    public void testDepartmentSetterAndGetters() {
        // Given
        Department department = new Department();

        Integer id = 1;
        String name = "Test Department";
        boolean active = false;

        // When
        department.setId(id);
        department.setName(name);
        department.setActive(active);

        // Then
        assertEquals(id, department.getId());
        assertEquals(name, department.getName());
        assertFalse(department.isActive());
    }

    @Test
    public void testDepartmentEqualsAndHashCode() {
        // Given
        Department department1 = new Department(1, "Department 1", true);
        Department department2 = new Department(1, "Department 1", true);

        // Then
        assertEquals(department1, department2);
        assertEquals(department1.hashCode(), department2.hashCode());
    }

    @Test
    public void testDepartmentNotEquals() {
        // Given
        Department department1 = new Department(1, "Department 1", true);
        Department department2 = new Department(2, "Department 2", false);

        // Then
        assertFalse(department1.equals(department2));
    }
    @Test
    public void testDepartmentToString() {
        // Given
        Department department = new Department(1, "Department 1", true);

        // Then
        assertEquals("Department(id=1, name=Department 1, active=true)", department.toString());
    }
    @Test
    public void testDepartmentRepositorySave() {
        // Given
        Department departmentToSave = new Department();
        departmentToSave.setId(1);
        departmentToSave.setName("Department 1");
        departmentToSave.setActive(true);

        // When
        when(departmentRepository.save(departmentToSave)).thenReturn(departmentToSave);
        Department savedDepartment = departmentRepository.save(departmentToSave);

        // Then
        assertEquals(departmentToSave, savedDepartment);
    }


}
