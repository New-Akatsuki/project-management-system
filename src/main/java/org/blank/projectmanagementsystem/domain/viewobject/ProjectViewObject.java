package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ProjectViewObject {
    private String name;
    private String client;
    private String projectManager;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private List<String> contractMembers;
    private List<String> focMembers;
    private String background;
    private String objective;
    private List<String> systemOutlines;
    private List<String> architectureOutlines;
    private List<String> deliverables;

    public ProjectViewObject(Project project){
        name = project.getName();
        client = project.getClient().getName();
        projectManager = project.getProjectManager().getName();
        department = project.getDepartment().getName();
        startDate = project.getStartDate();
        endDate = project.getEndDate();
        duration = project.getDuration();
        background = project.getBackground();
        objective = project.getObjective();
        contractMembers = project.getContractMembers().stream().map(User::getName).toList();
        focMembers = project.getFocMembers().stream().map(User::getName).toList();
        systemOutlines = project.getSystemOutlines().stream().map(SystemOutline::getName).toList();
        architectureOutlines = project.getArchitectures().stream().map(Architecture::getName).toList();
        deliverables = project.getDeliverables().stream().map(Deliverable::getName).toList();
    }
}
