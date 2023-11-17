package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.repository.AmountRepository;
import org.blank.projectmanagementsystem.service.AmountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AmountServiceImp implements AmountService {
    private final AmountRepository amountRepository;


    @Override
    public Amount save(Amount amount) {
        return amountRepository.save(amount);
    }

    @Override
    public List<Amount> getAllAmount() {
        return amountRepository.findAll();
    }

    @Override
    public List<Amount> findByProjectId(Long projectId) {
        return amountRepository.findByProjectId(projectId);
    }

    @Override
    public Amount findByProjectIdAndDevelopmentPhase(Long projectId, DevelopmentPhase developmentPhase) {
        return amountRepository.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
    }
}
