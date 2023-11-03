package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    Optional<User> findByName(String name);


//    Optional<User> findByUserId(Long userId);
}
