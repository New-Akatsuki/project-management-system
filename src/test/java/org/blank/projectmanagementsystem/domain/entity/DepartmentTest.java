package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {

    private Department department;
    private Department departmentWithBuilder;

    @BeforeEach
    public void setUp() {
        department = new Department();
        department.setId(1);
        department.setName("IT");
        department.setActive(true);

        departmentWithBuilder = Department.builder()
                .id(2)
                .name("HR")
                .active(true)
                .build();
    }

    @Test
    public void testGetId() {
        assertEquals(1, department.getId());
        assertEquals(2, departmentWithBuilder.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("IT", department.getName());
        assertEquals("HR", departmentWithBuilder.getName());
    }

    @Test
    public void testIsActive() {
        assertTrue(department.isActive());
        assertTrue(departmentWithBuilder.isActive());
    }

    @Test
    public void testEquals() {
        Department anotherDepartment = new Department();
        anotherDepartment.setId(1);
        anotherDepartment.setName("IT");
        anotherDepartment.setActive(true);

        Department anotherDepartmentWithBuilder = Department.builder()
                .id(2)
                .name("HR")
                .active(true)
                .build();

        assertEquals(department, anotherDepartment);
        assertEquals(departmentWithBuilder, anotherDepartmentWithBuilder);
    }

    @Test
    public void testNotEquals() {
          assertNotEquals(department, departmentWithBuilder);
    }
}

