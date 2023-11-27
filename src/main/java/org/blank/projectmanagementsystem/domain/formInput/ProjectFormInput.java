package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFormInput {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String background;
    private String objective;
    private List<Long> systemOutlines;
    private List<Long> architectureOutlines;
    private List<Long> deliverables;
    private List<Long> contractMembers;
    private List<Long> focMembers;
    private Long client;
}
