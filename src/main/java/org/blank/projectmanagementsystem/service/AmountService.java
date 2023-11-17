package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;

import java.util.List;

public interface AmountService {

        Amount save(Amount amount);
        List<Amount> getAllAmount();
        List<Amount> findByProjectId(Long projectId);

        Amount findByProjectIdAndDevelopmentPhase(Long projectId, DevelopmentPhase developmentPhase);




}
