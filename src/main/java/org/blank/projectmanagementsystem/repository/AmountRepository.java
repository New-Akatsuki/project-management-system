package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmountRepository extends JpaRepository<Amount, Long> {
    List<Amount> findByProjectId(Long projectId);

    Amount findByProjectIdAndDevelopmentPhase(Long projectId, DevelopmentPhase developmentPhase);

}
