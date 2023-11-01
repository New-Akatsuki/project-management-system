package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<List<Project>> findAllByProjectManager(User projectManager);
    Optional<List<Project>> findAllByDepartment(Department department);
    //get all project that user contains in the list of contract members or foc members
    @Query("SELECT p FROM Project p WHERE :user MEMBER OF p.contractMembers OR :user MEMBER OF p.focMembers")
    Optional<List<Project>> findAllProjectsByUserInMembers(@Param("user") User user);

}
