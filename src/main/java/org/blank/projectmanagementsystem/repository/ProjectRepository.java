package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.Enum.ProjectStatus;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findAllByProjectManager(User projectManager);
    List<Project> findAllByDepartment(Department department);
    @Query("SELECT p FROM Project p WHERE (:user MEMBER OF p.contractMembers OR :user MEMBER OF p.focMembers) AND p.status = 'ONGOING'")
    List<Project> findAllOngoingProjectsByUserInMembers(@Param("user") User user);
    @Query("SELECT p FROM Project p WHERE (p.projectManager = :user OR :user MEMBER OF p.contractMembers OR :user MEMBER OF p.focMembers) AND p.status = :status")
    Optional<List<Project>> findAllProjectsByUserInMembersAndStatus(@Param("user") User user,@Param("status") ProjectStatus status);
    List<Project> findByDepartmentId(Integer department_id);
}
