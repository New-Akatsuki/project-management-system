package org.blank.projectmanagementsystem.dto;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Amount;

@Data
public class AmountReportDto {
    private String name;
    private int amount;


    public AmountReportDto(Amount amount) {
        this.name = amount.getDevelopmentPhase().name();
        this.amount = amount.getAmount();
    }
}

