package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectFormInput {
    private String name;
    private Integer department;
    private Date startDate;
    private Date endDate;
    private String background;
    private String objective;
    private List<Integer> systemOutlines;
    private List<Integer> architectureOutlines;
    private List<Integer> deliverables;
    private List<Long> contractMembers;
    private List<Long> focMembers;
    private Long client;
}
