package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.repository.ReviewCountRepository;
import org.blank.projectmanagementsystem.service.AmountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReviewCountImpTest {

    @Mock
    private ReviewCountRepository reviewCountRepository;

    @Mock
    private AmountService amountService;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ReviewCountImp reviewCountService;

    @Test
    void testSaveOrUpdate_ExistingReviewCount() {
        // Mock data and dependencies
        ReviewDto reviewDto = new ReviewDto(1L,1L,ReviewerType.INTERNAL, DevelopmentPhase.BASIC_DESIGN,  5);
        Project project = new Project();
        project.setId(1L);
        when(projectRepository.findById(reviewDto.getProjectId())).thenReturn(Optional.of(project));

        ReviewCount existingReviewCount = new ReviewCount(1L,2,ReviewerType.INTERNAL, DevelopmentPhase.BASIC_DESIGN,project);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(
                reviewDto.getProjectId(), reviewDto.getDevelopmentPhase(), reviewDto.getReviewerType()))
                .thenReturn(existingReviewCount);

        when(reviewCountRepository.save(any(ReviewCount.class))).thenReturn(existingReviewCount);

        // Perform the operation
        ReviewDto savedReviewDto = reviewCountService.saveOrUpdate(reviewDto);

        assertEquals(existingReviewCount.getId(), savedReviewDto.getId());
        assertEquals(existingReviewCount.getCount(), savedReviewDto.getCount());
        // Add more assertions if needed
    }

    @Test
    void testSaveOrUpdate_NewReviewCount() {
        // Mock data and dependencies
        ReviewDto reviewDto = new ReviewDto(1L,1L,ReviewerType.INTERNAL, DevelopmentPhase.BASIC_DESIGN,  5);
        Project project = new Project();
        project.setId(1L);
        when(projectRepository.findById(reviewDto.getProjectId())).thenReturn(Optional.of(project));

        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(
                reviewDto.getProjectId(), reviewDto.getDevelopmentPhase(), reviewDto.getReviewerType()))
                .thenReturn(null);

        ReviewCount newReviewCount = new ReviewCount(1L,2,ReviewerType.INTERNAL, DevelopmentPhase.BASIC_DESIGN,project);
        when(reviewCountRepository.save(any(ReviewCount.class))).thenReturn(newReviewCount);

        // Perform the operation
        ReviewDto savedReviewDto = reviewCountService.saveOrUpdate(reviewDto);

        assertEquals(newReviewCount.getId(), savedReviewDto.getId());
        assertEquals(newReviewCount.getCount(), savedReviewDto.getCount());
        // Add more assertions if needed

    }

    @Test
    void testCalculateBasicDesignKpi() {
        // Mock data and dependencies
        Project project = new Project();
        project.setId(1L);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ReviewCount reviewCount = new ReviewCount(1L,2,ReviewerType.INTERNAL, DevelopmentPhase.BASIC_DESIGN,project);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(
                1L, DevelopmentPhase.BASIC_DESIGN, ReviewerType.INTERNAL))
                .thenReturn(reviewCount);

        Amount amount = new Amount();

        // Perform the operation
        double kpi = reviewCountService.calculateBasicDesignKpi(1L, DevelopmentPhase.BASIC_DESIGN, ReviewerType.INTERNAL);

        assertEquals(200, kpi);
        // Add more assertions if needed


    }

    @Test
    void testFindByProjectIdAndDevelopmentPhaseAndReviewerType() {
        // Mock data and dependencies
        Project project = new Project();
        project.setId(1L);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ReviewCount reviewCount = new ReviewCount(1L,2,ReviewerType.INTERNAL, DevelopmentPhase.BASIC_DESIGN,project);
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(
                1L, DevelopmentPhase.BASIC_DESIGN, ReviewerType.INTERNAL))
                .thenReturn(reviewCount);

        // Perform the operation
        ReviewCount foundReviewCount = reviewCountService.findByProjectIdAndDevelopmentPhaseAndReviewerType(
                1L, DevelopmentPhase.BASIC_DESIGN, ReviewerType.INTERNAL);

        assertEquals(reviewCount.getId(), foundReviewCount.getId());
        assertEquals(reviewCount.getCount(), foundReviewCount.getCount());
        // Add more assertions if needed


    }

    // Add more tests for other methods if needed
}

