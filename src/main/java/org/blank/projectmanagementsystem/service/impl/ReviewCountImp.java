package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.repository.ReviewCountRepository;
import org.blank.projectmanagementsystem.service.AmountService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReviewCountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewCountImp implements ReviewCountService {
    private final ReviewCountRepository reviewCountRepository;
    private final AmountService amountService;
    private final ProjectRepository projectRepository;

    @Override
    public ReviewDto saveOrUpdate(ReviewDto reviewDto) {
        Project project = projectRepository.findById(reviewDto.getProjectId()).orElseThrow();

        // Check if a ReviewCount already exists for the given project, development phase, and reviewer type
        ReviewCount existingReviewCount = reviewCountRepository
                .findByProjectIdAndDevelopmentPhaseAndReviewerType(
                        reviewDto.getProjectId(),
                        reviewDto.getDevelopmentPhase(),
                        reviewDto.getReviewerType());

        if (existingReviewCount != null) {
            // If the ReviewCount already exists, update its count
            existingReviewCount.setCount(reviewDto.getCount());
            reviewCountRepository.save(existingReviewCount);

            return ReviewDto.builder()
                    .id(existingReviewCount.getId())
                    .projectId(existingReviewCount.getProject().getId())
                    .developmentPhase(existingReviewCount.getDevelopmentPhase())
                    .reviewerType(existingReviewCount.getReviewerType())
                    .count(existingReviewCount.getCount())
                    .build();
        } else {
            // If the ReviewCount does not exist, create a new one
            ReviewCount newReviewCount = ReviewCount.builder()
                    .project(project)
                    .developmentPhase(reviewDto.getDevelopmentPhase())
                    .reviewerType(reviewDto.getReviewerType())
                    .count(reviewDto.getCount())
                    .build();
            reviewCountRepository.save(newReviewCount);

            return ReviewDto.builder()
                    .id(newReviewCount.getId())
                    .projectId(newReviewCount.getProject().getId())
                    .developmentPhase(newReviewCount.getDevelopmentPhase())
                    .reviewerType(newReviewCount.getReviewerType())
                    .count(newReviewCount.getCount())
                    .build();
        }
    }

    @Override
    public List<ReviewCount> getAllReviewCount() {
        return reviewCountRepository.findAll();
    }

    @Override
    public List<ReviewCount> findByProjectId(Long projectId) {
        return reviewCountRepository.findByProjectId(projectId);
    }

    @Override
    public double calculateBasicDesignKpi(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType) {
        ReviewCount reviewCount = reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(projectId, developmentPhase, reviewerType);
        if (reviewCount != null && reviewCount.getCount() > 0) {
            int reviewCountValue = reviewCount.getCount();

            switch (developmentPhase) {
                case BASIC_DESIGN, DETAILED_DESIGN -> {

                    Amount amount = amountService.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
                    return amount != null ? (double) amount.getAmount() / reviewCountValue : 0.0;
                }
                case CODING -> {
                    Amount amount = amountService.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
                    return amount != null && amount.getAmount() != 0 ? (double) (amount.getAmount()/1000)/ reviewCountValue : 0.0;
                }
                case UNIT_TESTING, INTEGRATION_TESTING -> {
                    Amount amount = amountService.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
                    return amount != null && amount.getAmount() != 0 ? ((double) amount.getAmount() / reviewCountValue) : 0.0;
                }
                default -> {
                    log.error("Unsupported development phase: {}", developmentPhase);
                    return -1;
                }
            }
        } else {
            log.error("ReviewCount not found for Project ID {} and Development Phase {}", projectId, developmentPhase);
            return -1;
        }
    }


    @Override
    public ReviewCount findByProjectIdAndDevelopmentPhaseAndReviewerType(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType) {
        return reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(projectId, developmentPhase,reviewerType);
    }

}
