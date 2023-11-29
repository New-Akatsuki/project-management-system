package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    public void setUp() {
        department = new Department();
        department.setName("Test Department");
        department.setActive(true);
    }

    @AfterEach
    public void tearDown() {
        departmentRepository.deleteAll();
        department = null;
    }

    @Test
    public void testFindByNameWhenDepartmentExistsThenReturnDepartment() {
        departmentRepository.save(department);
        Department foundDepartment = departmentRepository.findDepartmentByName(department.getName());
        assertEquals(department.getName(), foundDepartment.getName());
    }

    @Test
    public void testFindByNameWhenDepartmentDoesNotExistThenReturnNull() {
        Department foundDepartment = departmentRepository.findDepartmentByName("Nonexistent Department");
        assertNull(foundDepartment);
    }
}