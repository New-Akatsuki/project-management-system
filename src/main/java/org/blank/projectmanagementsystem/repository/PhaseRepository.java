package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {
    //find phase by project id
    List<Phase> findByProjectId(Long projectId);
}
