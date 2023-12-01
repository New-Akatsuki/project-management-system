package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;  // Inject the real ProjectRepository

    @Autowired
    private UserRepository userRepository;  // Inject the real UserRepository

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @Transactional  // Roll back the changes made during the test
    void findAllByProjectManager() {
        // Given
        Department department2 = new Department(null, "AA", true);
        departmentRepository.save(department2);

        User projectManager = User.builder()
                .name("Project Manager Officer")
                .username("pmo1")
                .email("pmo1@gmail.com")
                .password("Khun1234")
                .role(Role.PMO)
                .department(department2)
                .active(true)
                .build();

        // Save the User to the real database using the UserRepository
        userRepository.save(projectManager);

        // When
        List<Project> result = projectRepository.findAllByProjectManager(projectManager);

        // Then
        assertEquals(0, result.size());
    }
}
