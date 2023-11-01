package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ProjectFormInput {
    private String name;
    private Integer department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String background;
    private String objective;
    private List<Integer> systemOutlines;
    private List<Integer> architectureOutlines;
    private List<Integer> deliverables;
    private List<Long> contractMembers;
    private List<Long> focMembers;
    private Long client;
}
