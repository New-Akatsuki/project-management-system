package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
