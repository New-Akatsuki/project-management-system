package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.formInput.PhaseDto;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PhaseServiceImpl implements PhaseService {

    private final PhaseRepository phaseRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<Phase> getPhases(long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        return phaseRepository.findByProject(project);
    }

    @Override
    public PhaseDto createPhase(PhaseDto phaseDto) {
        Project project = projectRepository.findById(phaseDto.getProjectId()).orElseThrow();
        Phase phase = Phase.builder()
                .name(phaseDto.getName())
                .project(project)
                .build();
        phaseRepository.save(phase);
        return phaseDto.builder()
                .id(phase.getId())
                .name(phase.getName())
                .projectId(phase.getProject().getId())
                .build();
    }
}
