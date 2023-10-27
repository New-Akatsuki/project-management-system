package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.ReviewCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCategoryRepository extends JpaRepository<ReviewCategory, Integer> {
}
