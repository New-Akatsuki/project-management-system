package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;

import java.util.List;

public interface ReviewCountService {

//     boolean isDevelopmentPhaseExists(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType);
    ReviewDto save(ReviewDto reviewDto);

    List<ReviewCount> getAllReviewCount();

    double calculateBasicDesignKpi(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType);

    ReviewCount findByProjectIdAndDevelopmentPhaseAndReviewerType(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType);

}
