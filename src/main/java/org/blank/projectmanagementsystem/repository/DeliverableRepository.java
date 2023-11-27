package org.blank.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliverableRepository extends JpaRepository<Deliverable, Long> {
    List<Deliverable> findByStatusIsTrue();
}
