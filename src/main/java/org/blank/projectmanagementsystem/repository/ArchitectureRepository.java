package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchitectureRepository extends JpaRepository<Architecture, Integer>{
    Optional<Architecture> findByName(String name);








}
