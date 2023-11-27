package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.service.ArchitectureService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class ArchitectureServiceImpl implements ArchitectureService {
    private final ArchitectureRepository architectureRepository;

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public List<Architecture> getAllArchitectures() {
        return architectureRepository.findAll();
    }

    @Override
    public List<Architecture> getArchitecturesByStatusTrue() {
        return architectureRepository.findByStatusIsTrue();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Architecture updateArchitecture(Long id, Architecture architecture) {
        if (architectureRepository.existsById(id)) {
            architecture.setId(id);
            return architectureRepository.save(architecture);
        }
        return null;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Architecture save(Architecture architecture) {
        return architectureRepository.save(architecture);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Architecture getArchitectureById(Long id) {
        return architectureRepository.findById(id).orElse(null);
    }


}

