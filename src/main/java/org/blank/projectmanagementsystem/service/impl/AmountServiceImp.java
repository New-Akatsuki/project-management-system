package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.formInput.AmountDto;
import org.blank.projectmanagementsystem.repository.AmountRepository;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.service.AmountService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AmountServiceImp implements AmountService {
    private final AmountRepository amountRepository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;




    @Override
    public AmountDto saveOrUpdate(AmountDto amountFormInput) {
        // Check if the amount with the given project and development phase already exists
        Amount existingAmount = amountRepository.findByProjectIdAndDevelopmentPhase(
                amountFormInput.getProjectId(), amountFormInput.getDevelopmentPhase());

        if (existingAmount != null) {
            // Update existing amount
            existingAmount.setAmount(amountFormInput.getAmount());
            amountRepository.save(existingAmount);
            return AmountDto.builder()
                    .id(existingAmount.getId())
                    .projectId(existingAmount.getProject().getId())
                    .developmentPhase(existingAmount.getDevelopmentPhase())
                    .amount(existingAmount.getAmount())
                    .build();
        } else {
            // Save new amount
            Project project = projectRepository.findById(amountFormInput.getProjectId()).orElseThrow();
            Amount amount = Amount.builder()
                    .project(project)
                    .developmentPhase(amountFormInput.getDevelopmentPhase())
                    .amount(amountFormInput.getAmount())
                    .build();
            amountRepository.save(amount);
            return AmountDto.builder()
                    .id(amount.getId())
                    .projectId(amount.getProject().getId())
                    .developmentPhase(amount.getDevelopmentPhase())
                    .amount(amount.getAmount())
                    .build();
        }
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
