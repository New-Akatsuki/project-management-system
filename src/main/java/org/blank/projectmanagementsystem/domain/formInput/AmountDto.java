package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountDto {
    private Long id;
    private DevelopmentPhase developmentPhase;
    private int amount;
    private Long projectId;
}
