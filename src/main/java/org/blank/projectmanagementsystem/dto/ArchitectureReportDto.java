package org.blank.projectmanagementsystem.dto;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Architecture;

@Data
public class ArchitectureReportDto {
    private String name;
    private String type;

    public ArchitectureReportDto(Architecture architecture) {
        this.name = architecture.getName();
        this.type = architecture.getType().name();
    }
}
