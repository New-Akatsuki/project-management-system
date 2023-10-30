package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{

    @Query("SELECT MAX(t.id) FROM Task t")
    Long findMaxTaskId();

    List<Task> findByPhaseId(Long phaseId);
}
