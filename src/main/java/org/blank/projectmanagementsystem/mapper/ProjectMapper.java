package org.blank.projectmanagementsystem.mapper;

import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
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
    public String calculateDuration(LocalDate startDate, LocalDate endDate){
        int startYear = startDate.getYear();
        int startMonth = startDate.getMonthValue();
        int startDay = startDate.getDayOfMonth();
        int endYear = endDate.getYear();
        int endMonth = endDate.getMonthValue();
        int endDay = endDate.getDayOfMonth();
        int year = endYear - startYear;
        int month = endMonth - startMonth;
        int day = endDay - startDay;
        if(day < 0){
            month--;
            day += startDate.lengthOfMonth();
        }
        if(month < 0){
            year--;
            month += 12;
        }
        //check if year or month or day is 0, don't show it in duration
        StringBuilder duration = new StringBuilder();
        if(year != 0){
            duration.append(year).append(" year");
            if(year > 1) duration.append("s");
        }
        if(month != 0){
            if(year != 0) duration.append(" ");
            duration.append(month).append(" month");
            if(month > 1) duration.append("s");
        }
        if(day != 0){
            if(year != 0 || month != 0) duration.append(" ");
            duration.append(day).append(" day");
            if(day > 1) duration.append("s");
        }
        return duration.toString();

    }

    public static void main(String[] args) {
        ProjectMapper projectMapper = new ProjectMapper();
        System.out.println(projectMapper.calculateDuration(LocalDate.of(2021,1,1),LocalDate.of(2023,5,5)));
    }
}