package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;

import java.util.List;

public interface ReviewCountService {

//     boolean isDevelopmentPhaseExists(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType);
    ReviewDto saveOrUpdate(ReviewDto reviewDto);

    List<ReviewCount> getAllReviewCount();

    List<ReviewCount> findByProjectId(Long projectId);
    double calculateBasicDesignKpi(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType);

    ReviewCount findByProjectIdAndDevelopmentPhaseAndReviewerType(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType);

}
