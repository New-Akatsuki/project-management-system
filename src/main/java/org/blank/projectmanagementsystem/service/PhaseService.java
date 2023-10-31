package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.formInput.PhaseDto;

import java.util.List;

public interface PhaseService {
    List<Phase> getPhases(long projectId);
    PhaseDto createPhase(PhaseDto phaseDto);
}
