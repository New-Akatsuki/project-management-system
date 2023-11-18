package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Amount;

import java.util.List;

public interface AmountService {

        List<Amount> getAllAmount();
        List<Amount> findByProjectId(Long projectId);

        Amount findByProjectIdAndDevelopmentPhase(Long projectId, DevelopmentPhase developmentPhase);




}
