package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.TaskGroup;

import java.time.LocalDate;
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
    private LocalDate start_date;
    private LocalDate end_date;
    private LocalDate actual_due_date;
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
}
