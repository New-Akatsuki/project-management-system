package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Long projectId;
    private ReviewerType reviewerType;
    private DevelopmentPhase developmentPhase;
    private Integer count;
}
