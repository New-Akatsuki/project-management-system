package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;

import java.util.List;

public interface ReviewCountService {


    ReviewCount save(ReviewCount reviewCount);

    List<ReviewCount> getAllReviewCount();

    double calculateBasicDesignKpi(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType);

    ReviewCount findByProjectIdAndDevelopmentPhase(Long projectId, DevelopmentPhase developmentPhase);

}
