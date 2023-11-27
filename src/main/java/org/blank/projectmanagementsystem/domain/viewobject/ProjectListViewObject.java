package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ProjectListViewObject {
    private long id;
    private String name;
    private String client;
    private String projectManager;
    private String department;
    private LocalDate endDate;
    private String status;
    private int progress;

    public ProjectListViewObject(Project project, int progress){
        id = project.getId();
        name = project.getName();
        client = project.getClient().getName();
        projectManager = project.getProjectManager().getName();
        department = project.getDepartment().getName();
        endDate = project.getEndDate();
        status = project.getStatus().name();
        this.progress = progress;
    }
}
