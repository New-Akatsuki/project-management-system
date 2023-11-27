package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Amount;

@Data
public class AmountViewObject {
    private String name;
    private int amount;

    public AmountViewObject(Amount amount) {
        this.name = amount.getDevelopmentPhase().name();
        this.amount = amount.getAmount();
    }
}
