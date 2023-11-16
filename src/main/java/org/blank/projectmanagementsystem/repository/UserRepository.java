package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.viewobject.AllIssueDisplayViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.UserEditViewObject;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    //    Optional<User> findByEmail(String email);
//    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    //select user where username = ? or email = ?
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> getReferenceByUsername(String username);

    Long countByDepartment(Department department);

//    @Query("SELECT new org.blank.projectmanagementsystem.domain.viewobject.UserEditViewObject(i.id, i.imgUrl, i.name, i.username, i.userRole,i.phone,i.email) FROM User i WHERE i.id = ?1")
//    Optional<UserViewObject> findByUserId(Long id);


}
