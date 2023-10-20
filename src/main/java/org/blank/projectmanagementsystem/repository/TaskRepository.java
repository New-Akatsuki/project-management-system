package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
