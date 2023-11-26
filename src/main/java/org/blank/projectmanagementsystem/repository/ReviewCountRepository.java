package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCountRepository extends JpaRepository<ReviewCount, Long>{
    // Find by project ID and development phase

    // Find by project ID, development phase, and reviewer type
    ReviewCount findByProjectIdAndDevelopmentPhaseAndReviewerType(Long projectId, DevelopmentPhase name, ReviewerType reviewerType);

    // Find by project ID and development phase
    ReviewCount findByProjectIdAndDevelopmentPhase(Long projectId, DevelopmentPhase developmentPhase);


}
