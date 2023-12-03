package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEditViewObject{
    private String name;
    private Long clientId;
    private List<Long> contractMemberIds;
    private List<Long> focMemberIds;
    private Integer departmentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> deliverableIds;
    private List<Long> arcOutlineIds;
    private List<Long> sysOutlineIds;
    private String objective;
    private String background;

    public ProjectEditViewObject(Project project){
        this.name = project.getName();
        this.clientId = project.getClient().getId();
        this.contractMemberIds = project.getContractMembers().stream().map(User::getId).toList();
        this.focMemberIds = project.getFocMembers().stream().map(User::getId).toList();
        this.departmentId = project.getDepartment().getId();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.deliverableIds = project.getDeliverables().stream().map(Deliverable::getId).toList();
        this.arcOutlineIds = project.getArchitectures().stream().map(Architecture::getId).toList();
        this.sysOutlineIds = project.getSystemOutlines().stream().map(SystemOutline::getId).toList();
        this.objective = project.getObjective();
        this.background = project.getBackground();
    }
}
