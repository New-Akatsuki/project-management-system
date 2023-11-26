package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.repository.ReviewCountRepository;
import org.blank.projectmanagementsystem.service.impl.ReviewCountImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewCountRepositoryTest {

    @Mock
    private ReviewCountRepository reviewCountRepository;

    @InjectMocks
    private ReviewCountImp reviewCountService;

    @Test
    public void testFindByProjectIdAndDevelopmentPhaseAndReviewerType() {
        // Given
        Long projectId = 1L;
        DevelopmentPhase developmentPhase = DevelopmentPhase.PLANNING;
        ReviewerType reviewerType = ReviewerType.INTERNAL;
        ReviewCount reviewCount = new ReviewCount();
        reviewCount.setProjectId(projectId);
        reviewCount.setDevelopmentPhase(developmentPhase);
        reviewCount.setReviewerType(reviewerType);

        // When
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhaseAndReviewerType(projectId, developmentPhase, reviewerType))
                .thenReturn(reviewCount);

        // Then
        ReviewCount foundReviewCount = reviewCountService.findByProjectIdAndDevelopmentPhaseAndReviewerType(projectId, developmentPhase, reviewerType);
        assertThat(foundReviewCount).isEqualTo(reviewCount);
    }

    @Test
    public void testFindByProjectIdAndDevelopmentPhase() {
        // Given
        Long projectId = 1L;
        DevelopmentPhase developmentPhase = DevelopmentPhase.PLANNING;
        ReviewCount reviewCount = new ReviewCount();
        reviewCount.setProjectId(projectId);
        reviewCount.setDevelopmentPhase(developmentPhase);

        // When
        when(reviewCountRepository.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase)).thenReturn(reviewCount);

        // Then
        ReviewCount foundReviewCount = reviewCountService.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
        assertThat(foundReviewCount).isEqualTo(reviewCount);
    }

    // Add more test cases as needed based on your repository methods
}

