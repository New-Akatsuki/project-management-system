package org.blank.projectmanagementsystem.domain.viewobject;

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
public class TaskViewObject {
    private Long id;
    private String name;
    private String description;
    private String priority;
    private String start_date;
    private String end_date;
    private String actual_due_date;
    private Integer duration;
    private Float plan_hours;
    private Float actual_hours;
    private int progress;
    private boolean status;
    private Long parent;
    private String group;
    private String type;
    private List<Long> assignees;
    private Boolean open;
    private Long phase;
}
