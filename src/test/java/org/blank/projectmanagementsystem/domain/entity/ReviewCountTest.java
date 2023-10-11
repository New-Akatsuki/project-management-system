package org.blank.projectmanagementsystem.domain.entity;

import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewCountTest {

    private ReviewCount reviewCount;
    private ReviewCategory reviewCategory;
    private Project project;

    @BeforeEach
    public void setUp() {
        reviewCategory = new ReviewCategory(); // Initialize ReviewCategory object appropriately
        project = new Project(); // Initialize Project object appropriately

        reviewCount = new ReviewCount();
        reviewCount.setId(1);
        reviewCount.setCount(5);
        reviewCount.setReviewerType(ReviewerType.INTERNAL);
        reviewCount.setReviewCategory(reviewCategory);
        reviewCount.setProject(project);
    }

    @Test
    public void testReviewCountAttributes() {
        assertEquals(1, reviewCount.getId());
        assertEquals(5, reviewCount.getCount());
        assertEquals(ReviewerType.INTERNAL, reviewCount.getReviewerType());
        assertEquals(reviewCategory, reviewCount.getReviewCategory());
        assertEquals(project, reviewCount.getProject());
    }

    @Test
    public void testEquals() {
        ReviewCount sameReviewCount = new ReviewCount();
        sameReviewCount.setId(1);
        sameReviewCount.setCount(5);
        sameReviewCount.setReviewerType(ReviewerType.INTERNAL);
        sameReviewCount.setReviewCategory(reviewCategory);
        sameReviewCount.setProject(project);

        assertEquals(reviewCount, sameReviewCount);
    }

    @Test
    public void testNotEquals() {
        ReviewCount differentReviewCount = new ReviewCount();
        differentReviewCount.setId(2);
        differentReviewCount.setCount(10);
        differentReviewCount.setReviewerType(ReviewerType.EXTERNAL);
        differentReviewCount.setReviewCategory(new ReviewCategory()); // Initialize appropriately
        differentReviewCount.setProject(new Project()); // Initialize appropriately

        assertNotEquals(reviewCount, differentReviewCount);
    }

    @Test
    public void testHashCode() {
        ReviewCount sameReviewCount = new ReviewCount();
        sameReviewCount.setId(1);
        sameReviewCount.setCount(5);
        sameReviewCount.setReviewerType(ReviewerType.INTERNAL);
        sameReviewCount.setReviewCategory(reviewCategory);
        sameReviewCount.setProject(project);

        assertEquals(reviewCount.hashCode(), sameReviewCount.hashCode());
    }
}
