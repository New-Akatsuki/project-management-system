package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.SystemOutline;

import java.util.List;

public interface SystemOutlineService {
    List<SystemOutline> getAllSystemOutlines();

    SystemOutline save(SystemOutline systemOutline);

    SystemOutline getSystemOutlineById(Long id);

    List<SystemOutline> getAllSystemOutlineByStatusTrue();
}
