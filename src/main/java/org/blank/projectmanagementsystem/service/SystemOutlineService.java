package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.SystemOutline;

public interface SystemOutlineService {
    SystemOutline save(SystemOutline systemOutline);
    void saveName(String name);
    void updateSystemOutline(SystemOutline systemOutline);
    void deleteSystemOutline();
}
