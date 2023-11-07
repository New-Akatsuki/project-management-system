package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    Project saveProject(ProjectFormInput projectFormInput, String pmUsername);
    List<ProjectViewObject> getAllProjects();
    List<User> getProjectMembers(Long projectId);
    List<User> getUsersByOngoingProject();
    Map<String,List<Object>> getUsersAndClientByOngoingProject();

    Project getProject(long projectId);
}
