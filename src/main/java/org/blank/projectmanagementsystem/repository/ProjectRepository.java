package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<List<Project>> findAllByProjectManager(User projectManager);
    Optional<List<Project>> findAllByDepartment(Department department);
    //get all project that user contains in the list of contract members
    @Query("SELECT p FROM Project p INNER JOIN p.contractMember u WHERE u = :user")
    List<Project> findAllByContractMember(User user);
}
