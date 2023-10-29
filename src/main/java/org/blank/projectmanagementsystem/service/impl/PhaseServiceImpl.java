package org.blank.projectmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.service.PhaseService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class PhaseServiceImpl implements PhaseService  {

    private final PhaseRepository phaseRepository;
    @Override
    public List<Phase> getAllPhase() {
        return phaseRepository.findAll();
    }

    @Override
    public Phase createPhase(Phase phase) {
        return phaseRepository.save(phase);
    }

    @Override
    public Phase updatePhase(Phase phase) {
        return phaseRepository.save(phase);
    }

    @Override
    public void deletePhase(Long id) {

    }
}
