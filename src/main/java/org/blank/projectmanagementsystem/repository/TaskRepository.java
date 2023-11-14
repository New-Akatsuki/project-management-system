package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>{

    //get all task by parentTask
    Iterable<Task> findAllByParentTask(Task parentTask);
    Iterable<Task> findAllByPhase(Phase phase);
    //get all task by assginee id
    List<Task> findAllByAssignees_Id(Long id);
}
