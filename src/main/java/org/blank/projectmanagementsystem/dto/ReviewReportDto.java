package org.blank.projectmanagementsystem.dto;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;

@Data
public class ReviewReportDto {
    private String name;
    private String type;
    private int count;

    public ReviewReportDto(ReviewCount reviewCount) {
        this.type = reviewCount.getReviewerType().name();
        this.name = reviewCount.getDevelopmentPhase().name();
        this.count = reviewCount.getCount();
    }
}
