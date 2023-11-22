package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.formInput.AmountDto;

import java.util.List;

public interface AmountService {

//        boolean isDevelopmentPhaseExists(Long projectId, DevelopmentPhase developmentPhase);

        AmountDto saveOrUpdate(AmountDto amountFormInput);
        List<Amount> getAllAmount();
        List<Amount> findByProjectId(Long projectId);

        Amount findByProjectIdAndDevelopmentPhase(Long projectId, DevelopmentPhase developmentPhase);




}
