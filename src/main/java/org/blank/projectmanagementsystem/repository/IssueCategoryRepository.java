package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueCategoryRepository extends JpaRepository<IssueCategory, Long>{
}
