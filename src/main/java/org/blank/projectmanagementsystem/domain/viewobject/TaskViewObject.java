package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.TaskGroup;
import org.blank.projectmanagementsystem.domain.entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskViewObject {
    private Long id;
    private String name;
    private String description;
    private String priority;
    private LocalDate start_date;
    private LocalDate end_date;
    private LocalDate actual_due_date;
    private int duration;
    private Float plan_hours;
    private Float actual_hours;
    private int progress;
    private boolean status;
    private Long phase;
    private Long projectId;
    private Long parent;
    private String group;
    private String type;
    private Set<Assignee> assignees;
    private Boolean open;
}
