package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.TaskGroup;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskFormInput {
    private Long id;
    private String name;
    private String description;
    private String priority;
    private Date start_date;
    private Date end_date;
    private Date actual_due_date;
    private int duration;
    private float plan_hours;
    private float actual_hours;
    private int progress;
    private boolean status;
    private Long parent;
    private String group;
    private String type;
    private List<Long> assignees;
    private List<Long> subtasks;
    private Boolean open;
    private Long phase;
}
