package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
