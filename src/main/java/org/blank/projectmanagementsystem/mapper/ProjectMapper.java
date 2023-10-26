package org.blank.projectmanagementsystem.mapper;

import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.springframework.security.core.parameters.P;

import java.util.Calendar;
import java.util.Date;

public class ProjectMapper {
    //FormInput to Entity
    public Project mapToProject(ProjectFormInput projectFormInput){
        return Project.builder()
                .name(projectFormInput.getName())
                .background(projectFormInput.getBackground())
                .objective(projectFormInput.getObjective())
                .duration(calculateDuration(projectFormInput.getStartDate(),projectFormInput.getEndDate()))
                .startDate(projectFormInput.getStartDate())
                .endDate(projectFormInput.getEndDate())
                .build();
    }

    //Entity to ViewObject
    public ProjectViewObject mapToProjectViewObject(Project project){
        return null;
    }

    //calculate duration by month and day from start date and end date like 1 month 2 days
    public String calculateDuration(Date startDate, Date endDate){
        int startYear = startDate.getYear();
        int startMonth = startDate.getMonth();
        int startDay = startDate.getDay();
        int endYear = endDate.getYear();
        int endMonth = endDate.getMonth();
        int endDay = endDate.getDay();
        int durationYear = endYear - startYear;
        int durationMonth = endMonth - startMonth;
        int durationDay = endDay - startDay;
        if(durationDay < 0){
            durationDay += 30;
            durationMonth -= 1;
        }
        if(durationMonth < 0){
            durationMonth += 12;
            durationYear -= 1;
        }
        StringBuilder duration = new StringBuilder();
        if(durationYear == 1){
            duration.append(durationYear).append(" Year ");
        }
        if (durationYear > 1){
            duration.append(durationYear).append(" Years ");
        }
        if(durationMonth == 1){
            duration.append(durationMonth).append(" Month ");
        }
        if (durationMonth > 1){
            duration.append(durationMonth).append(" Months ");
        }
        if(durationDay == 1){
            duration.append(durationDay).append(" Day ");
        }
        if (durationDay > 1){
            duration.append(durationDay).append(" Days ");
        }
        return duration.toString();
    }
}