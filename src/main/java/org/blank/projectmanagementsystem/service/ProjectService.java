package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.Role;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;

import java.util.List;

public interface ProjectService {
    Project saveProject(ProjectFormInput projectFormInput);
    List<ProjectViewObject> getAllProjects();
}
