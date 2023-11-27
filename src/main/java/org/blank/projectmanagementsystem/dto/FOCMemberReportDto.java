package org.blank.projectmanagementsystem.dto;

import lombok.Data;

@Data
public class FOCMemberReportDto {
    private String name;

    public FOCMemberReportDto(String name){
        this.name = name;
    }
}
