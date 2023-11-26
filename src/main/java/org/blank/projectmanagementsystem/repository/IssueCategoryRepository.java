package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueCategoryRepository extends JpaRepository<IssueCategory, Long>{
    IssueCategory findIssueCategoryByName(String categoryName);

    void deleteByName(String categoryName);

    List<IssueCategory> findAllByName(String categoryName);
}
