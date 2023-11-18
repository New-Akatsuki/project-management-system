package org.blank.projectmanagementsystem.mapper;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.Priority;
import org.blank.projectmanagementsystem.domain.Enum.TaskGroup;
import org.blank.projectmanagementsystem.domain.Enum.TaskType;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.Assignee;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.springframework.cglib.core.Local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TaskMapper {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public TaskViewObject mapToTaskViewObject(Task task){
        return TaskViewObject.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priority(task.getPriority().name().toLowerCase())
                .start_date(task.getStartDate())
                .end_date(task.getDueDate())
                .actual_due_date(task.getActualDueDate())
                .duration(calculateDurationOnlyWeekday(task.getStartDate(), task.getDueDate()))
                .plan_hours(task.getPlanHours())
                .actual_hours(task.getActualHours())
                .progress(task.isStatus()?1:0)
                .status(task.isStatus())
                .projectId(task.getProject()==null?null:task.getProject().getId())
                .phase(task.getPhase().getId())
                .parent(task.getParentTask()==null?null:task.getParentTask().getId())
                .group(task.getGroup().name())
                .type(task.getType().name().toLowerCase())
                .assignees(task.getAssignees()==null?new HashSet<>():task.getAssignees().stream().map(Assignee::new).collect(Collectors.toSet()))
                .open(true)
                .build();
    }

    public Task mapToTask(TaskFormInput taskFormInput){
        return Task.builder()
                .name(taskFormInput.getName())
                .description(taskFormInput.getDescription())
                .priority(Priority.valueOf(taskFormInput.getPriority().toUpperCase()))
                .startDate(taskFormInput.getStart_date())
                .dueDate(taskFormInput.getEnd_date())
                .actualDueDate(taskFormInput.getActual_due_date())
                .planHours(taskFormInput.getPlan_hours())
                .actualHours(taskFormInput.getActual_hours())
                .status(taskFormInput.isStatus())
                .group(TaskGroup.valueOf(taskFormInput.getGroup()==null?"A":taskFormInput.getGroup().toUpperCase()))
                .type(TaskType.valueOf(taskFormInput.getType().toUpperCase()))
                .build();
    }

    private LocalDate convertStringToDate(String date) {
        if(date == null) return null;
        try {
            return dateFormat.parse(date).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int calculateDuration(LocalDate startDate, LocalDate endDate){
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }

    //calculateDuration only weekday
    private int calculateDurationOnlyWeekday(LocalDate startDate, LocalDate endDate){
        int duration = 0;
        while(startDate.isBefore(endDate)){
            if(startDate.getDayOfWeek().getValue() < 6){
                duration++;
            }
            startDate = startDate.plusDays(1);
        }
        return duration;
    }

    public static void main(String[] args) {
        TaskMapper taskMapper = new TaskMapper();
        System.out.println(taskMapper.calculateDurationOnlyWeekday(
                LocalDate.of(2023, Month.NOVEMBER, 1),
                LocalDate.of(2023, Month.NOVEMBER, 8)));
    }

}
