package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectRepositoryTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    public void testFindAllByProjectManager() {
        // Given
        User projectManager = new User();
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = Arrays.asList(project1, project2);

        // When
        when(projectRepository.findAllByProjectManager(projectManager)).thenReturn(projects);

        // Then
        List<Project> foundProjects = projectService.findAllByProjectManager(projectManager);
        assertThat(foundProjects).isEqualTo(projects);
    }

    @Test
    public void testFindAllByDepartment() {
        // Given
        Department department = new Department();
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = Arrays.asList(project1, project2);

        // When
        when(projectRepository.findAllByDepartment(department)).thenReturn(projects);

        // Then
        List<Project> foundProjects = projectService.findAllByDepartment(department);
        assertThat(foundProjects).isEqualTo(projects);
    }

    @Test
    public void testFindAllProjectsByUserInMembers() {
        // Given
        User user = new User();
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = Arrays.asList(project1, project2);

        // When
        when(projectRepository.findAllProjectsByUserInMembers(user)).thenReturn(projects);

        // Then
        List<Project> foundProjects = projectService.findAllProjectsByUserInMembers(user);
        assertThat(foundProjects).isEqualTo(projects);
    }



    @Test
    public void testFindByDepartmentId() {
        // Given
        Integer departmentId = 1;
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> projects = Arrays.asList(project1, project2);

        // When
        when(projectRepository.findByDepartmentId(departmentId)).thenReturn(projects);

        // Then
        List<Project> foundProjects = projectService.findByDepartmentId(departmentId);
        assertThat(foundProjects).isEqualTo(projects);
    }
}
