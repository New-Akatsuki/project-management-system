package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Phase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhaseDto {
    private Long id;
    private String name;
    private long projectId;
}
