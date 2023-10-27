package org.blank.projectmanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SystemOutlineServiceImpl implements SystemOutlineService{

    private final SystemOutlineRepository systemOutlineRepository;

    @Override
    public SystemOutline save(SystemOutline systemOutline) {
        return systemOutlineRepository.save(systemOutline);
    }

    @Override
    public void saveName(String name) {
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setName(name);
        systemOutlineRepository.save(systemOutline);
    }
    @Override
    public void updateSystemOutline(SystemOutline systemOutline) {
        systemOutlineRepository.save(systemOutline);
    }
    @Override
    public void deleteSystemOutline() {
        systemOutlineRepository.deleteAll();
    }




}
