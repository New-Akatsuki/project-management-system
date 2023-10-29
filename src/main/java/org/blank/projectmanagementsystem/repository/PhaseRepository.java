package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {

}
