package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.dto.PhaseDto;

import java.util.List;

public interface PhaseService {
    List<PhaseDto> getPhases(long projectId);
    PhaseDto createPhase(PhaseDto phaseDto);
    PhaseDto updatePhase(PhaseDto phaseDto);
    void deletePhase(long phaseId);
}
