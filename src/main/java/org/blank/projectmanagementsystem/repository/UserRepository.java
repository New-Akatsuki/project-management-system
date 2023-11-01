package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByNameAndPassword(String name, String password);
    User findByEmail(String email);

//    Optional<User> findByUserId(Long userId);
}
