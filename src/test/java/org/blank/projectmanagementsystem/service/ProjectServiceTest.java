//package org.blank.projectmanagementsystem.service;
//import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
//import org.blank.projectmanagementsystem.domain.entity.Department;
//import org.blank.projectmanagementsystem.domain.entity.Project;
//import org.blank.projectmanagementsystem.domain.entity.User;
//import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
//import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
//import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
//import org.blank.projectmanagementsystem.repository.ProjectRepository;
//import org.blank.projectmanagementsystem.service.impl.ProjectServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ProjectServiceTest {
//
//    @Mock
//    private ProjectRepository projectRepository;
//
//    @InjectMocks
//    private ProjectServiceImpl projectService;
//
//    @Test
//    public void testFindByDepartmentById(){
//        //given
//        Department department = new Department();
//        department.setId(1);
//        Project project = new Project();
//        project.setId(1L);
//        project.setDepartment(department);
//        when(projectRepository.findByDepartmentId(1)).thenReturn(Arrays.asList(project));
//
//        //when
//        List<Project> projects = projectService.findByDepartmentId(1);
//
//        //then
//        assertThat(projects).hasSize(1);
//        assertThat(projects.get(0).getId()).isEqualTo(1L);
//        assertThat(projects.get(0).getDepartment().getId()).isEqualTo(1);
//    }
//
//
//
//}
//
//
