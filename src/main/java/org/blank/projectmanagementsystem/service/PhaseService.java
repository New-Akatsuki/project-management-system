package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Phase;

import java.util.List;

public interface PhaseService {

    List<Phase> getAllPhase();
    Phase createPhase(Phase phase);

    Phase updatePhase(Phase phase);

    void deletePhase(Long id);

    Phase getPhaseById(Long id);

}
