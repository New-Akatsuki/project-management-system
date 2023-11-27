package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemOutlineServiceImpl implements SystemOutlineService {

    private final SystemOutlineRepository systemOutlineRepository;

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public SystemOutline save(SystemOutline systemOutline) {
        return systemOutlineRepository.save(systemOutline);
    }


    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public SystemOutline getSystemOutlineById(Long id) {
        return systemOutlineRepository.findById(id).orElse(null);
    }

    @Override
    public List<SystemOutline> getAllSystemOutlineByStatusTrue() {
        return systemOutlineRepository.findByStatusIsTrue();
    }


    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public List<SystemOutline> getAllSystemOutlines() {
        return systemOutlineRepository.findAll();
    }
}
