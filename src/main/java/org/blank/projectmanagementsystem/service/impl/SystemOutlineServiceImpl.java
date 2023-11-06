package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemOutlineServiceImpl implements SystemOutlineService {

    private final SystemOutlineRepository systemOutlineRepository;

    @Override
    public SystemOutline save(SystemOutline systemOutline) {
        return systemOutlineRepository.save(systemOutline);
    }


    @Override
    public SystemOutline getSystemOutlineById(Long id) {
        return systemOutlineRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    @Override
    public void deleteSystemOutline(Long id) {
        systemOutlineRepository.deleteById(id);
    }

    @Override
    public List<SystemOutline> getAllSystemOutlines() {
        return systemOutlineRepository.findAll();
    }
}
