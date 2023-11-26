package org.blank.projectmanagementsystem.dto;

import lombok.Data;

@Data
public class ContractMemberReportDto {
    private String name;

    public ContractMemberReportDto(String name){
        this.name = name;
    }
}
