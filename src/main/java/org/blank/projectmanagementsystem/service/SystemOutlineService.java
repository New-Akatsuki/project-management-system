package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.SystemOutline;

import java.util.List;

public interface SystemOutlineService {
    List<SystemOutline> getAllSystemOutlines();

    SystemOutline saveName(String name);

    void updateSystemOutline(SystemOutline systemOutline);

     SystemOutline findById(Integer id);
    SystemOutline save(SystemOutline systemOutline);
    void delete(Integer id);


    void addSystemOutline(String newSystemOutline);
}
