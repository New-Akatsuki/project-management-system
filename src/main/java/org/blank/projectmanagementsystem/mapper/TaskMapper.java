package org.blank.projectmanagementsystem.mapper;

import org.blank.projectmanagementsystem.domain.Enum.Priority;
import org.blank.projectmanagementsystem.domain.Enum.TaskGroup;
import org.blank.projectmanagementsystem.domain.Enum.TaskType;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.TaskFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.TaskViewObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskMapper {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public TaskViewObject mapToTaskViewObject(Task task){
        return TaskViewObject.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priority(task.getPriority().name().toLowerCase())
                .start_date(convertDateToString(task.getStartDate()))
                .end_date(convertDateToString(task.getDueDate()))
                .actual_due_date(convertDateToString(task.getActualDueDate()))
                .duration(calculateDuration(task.getStartDate(), task.getDueDate()))
                .plan_hours(task.getPlanHours())
                .actual_hours(task.getActualHours())
                .progress(task.isStatus()?1:0)
                .status(task.isStatus())
                .parent(task.getParentTask()==null?null:task.getParentTask().getId())
                .group(task.getGroup().name().toLowerCase())
                .type(task.getType().name().toLowerCase())
                .assignees(task.getAssignees()==null?null:task.getAssignees().stream().map(User::getId).toList())
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
                .status(taskFormInput.getProgress() == 1)
                .group(TaskGroup.valueOf(taskFormInput.getGroup()==null?"A":taskFormInput.getGroup().toUpperCase()))
                .type(TaskType.valueOf(taskFormInput.getType().toUpperCase()))
                .build();
    }
    public String convertDateToString(Date date) {
        if(date == null) return null;
//        date.setHours(0);
//        date.setMinutes(0);
//        date.setSeconds(0);
//        date.setTime((date.getTime() / 1000) * 1000);
        return dateFormat.format(date);
    }

    private Date convertStringToDate(String date) {
        if(date == null) return null;
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculateDuration(Date startDate, Date endDate){
        return (int) (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static void main(String[] args) {
        TaskMapper taskMapper = new TaskMapper();
        System.out.println(taskMapper.convertStringToDate("2021-05-31T17:00:00.000Z"));
        System.out.println(taskMapper.convertDateToString(new Date()));
        System.out.println(taskMapper.convertStringToDate(taskMapper.convertDateToString(new Date())));
    }
}
