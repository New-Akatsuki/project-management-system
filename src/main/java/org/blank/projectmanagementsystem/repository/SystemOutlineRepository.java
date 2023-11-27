package org.blank.projectmanagementsystem.repository;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemOutlineRepository extends JpaRepository<SystemOutline, Long>{

    List<SystemOutline> findByStatusIsTrue();

}
