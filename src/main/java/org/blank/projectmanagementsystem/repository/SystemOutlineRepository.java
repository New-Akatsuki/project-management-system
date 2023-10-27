package org.blank.projectmanagementsystem.repository;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SystemOutlineRepository extends JpaRepository<SystemOutline, Integer>{
    SystemOutline findByName(String systemOutlineName);
}
