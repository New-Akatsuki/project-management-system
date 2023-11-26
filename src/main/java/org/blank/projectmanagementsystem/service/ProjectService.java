package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectListViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    Project saveProject(ProjectFormInput projectFormInput);
    List<ProjectListViewObject> getAllProjects();
    ProjectViewObject getProjectById(Long id);
    List<User> getProjectMembers(Long projectId);
    List<User> getUsersByOngoingProject();
    Map<String,List<Object>> getUsersAndClientByOngoingProject();
    List<ProjectViewObject> getProjectsByDepartment(Integer departmentId);
    Project getProject(long projectId);
    Project getProjectByID(Long id);
    Project updateProject(ProjectFormInput projectFormInput);

}
