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
    private final ProjectService projectService;


//    @Override
//    public boolean isDevelopmentPhaseExists(Long projectId, DevelopmentPhase developmentPhase, ReviewerType reviewerType) {
//        return reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(projectId, developmentPhase, reviewerType) != null;
//    }


    @Override
    public ReviewDto save(ReviewDto reviewDto) {
        Project project = projectRepository.findById(reviewDto.getProjectId()).orElseThrow();
        ReviewCount reviewCount = ReviewCount.builder()
                .project(project)
                .developmentPhase(reviewDto.getDevelopmentPhase())
                .reviewerType(reviewDto.getReviewerType())
                .count(reviewDto.getCount())
                .build();
        reviewCountRepository.save(reviewCount);
        return ReviewDto.builder()
                .id(reviewCount.getId())
                .projectId(reviewCount.getProject().getId())
                .developmentPhase(reviewCount.getDevelopmentPhase())
                .reviewerType(reviewCount.getReviewerType())
                .count(reviewCount.getCount())
                .build();
    }

    @Override
    public List<ReviewCount> getAllReviewCount() {
        return reviewCountRepository.findAll();
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
