package org.blank.projectmanagementsystem.repository;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemOutlineRepository extends JpaRepository<SystemOutline, Long>{
    SystemOutline findByName(String systemOutlineName);
    Optional<SystemOutline> findById(Integer id);





}
