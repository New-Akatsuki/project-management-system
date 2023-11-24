package org.blank.projectmanagementsystem.dto;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectReportDto {
    private String projectName;
    private String clientName;
    private String projectManagerName;
    private String departmentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private List<String> contractMembers;
    private List<String> focMembers;
    private String background;
    private String objective;
    private List<String> deliverables;
    private List<String> systemOutlines;
    private List<AmountReportDto> amounts;
    private List<ArchitectureReportDto> architectures;
    private List<ReviewReportDto> reviewCounts;

    public ProjectReportDto(Project project) {
        this.projectName = project.getName();
        this.clientName = project.getClient().getName();
        this.projectManagerName = project.getProjectManager().getName();
        this.departmentName = project.getDepartment().getName();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.duration = project.getDuration();
        this.contractMembers = project.getContractMembers().stream().map(User::getName).toList();
        this.focMembers = project.getFocMembers().stream().map(User::getName).toList();
        this.background = project.getBackground();
        this.objective = project.getObjective();
        this.architectures = project.getArchitectures().stream().map(ArchitectureReportDto::new).toList();
        this.deliverables = project.getDeliverables().stream().map(Deliverable::getName).toList();
        this.systemOutlines = project.getSystemOutlines().stream().map(SystemOutline::getName).toList();
        this.amounts = project.getAmounts().stream().map(AmountReportDto::new).toList();
        this.reviewCounts = project.getReviewCounts().stream().map(ReviewReportDto::new).toList();
    }

}
