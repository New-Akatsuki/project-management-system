package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewCategoryTest {

    private ReviewCategory reviewCategory;

    @BeforeEach
    public void setUp() {
        reviewCategory = new ReviewCategory();
        reviewCategory.setId(1);
        reviewCategory.setName("Quality");

        // You can also use the builder pattern if you have a builder method in your ReviewCategory class.
        // reviewCategory = ReviewCategory.builder().id(1).name("Quality").build();
    }

    @Test
    public void testReviewCategoryAttributes() {
        assertEquals(1, reviewCategory.getId());
        assertEquals("Quality", reviewCategory.getName());
    }

    @Test
    public void testEquals() {
        ReviewCategory sameReviewCategory = new ReviewCategory();
        sameReviewCategory.setId(1);
        sameReviewCategory.setName("Quality");

        assertEquals(reviewCategory, sameReviewCategory);
    }

    @Test
    public void testNotEquals() {
        ReviewCategory differentReviewCategory = new ReviewCategory();
        differentReviewCategory.setId(2);
        differentReviewCategory.setName("Performance");

        assertNotEquals(reviewCategory, differentReviewCategory);
    }

    @Test
    public void testHashCode() {
        ReviewCategory sameReviewCategory = new ReviewCategory();
        sameReviewCategory.setId(1);
        sameReviewCategory.setName("Quality");

        assertEquals(reviewCategory.hashCode(), sameReviewCategory.hashCode());
    }
}

