package org.blank.projectmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverableRepository extends JpaRepository<Deliverable, Integer> {
      Deliverable findByName(String name);

}
