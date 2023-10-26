package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Role;
import org.blank.projectmanagementsystem.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    void saveRole(Role role);
    void saveDepartment(Department department);
    void changeDefaultPassword(String password);
    void changePassword(String currentPassword, String newPassword);
    List<User> getAllUser();

//    Optional<User> getUserById(Long userId);

    User updateUser(User user);

}
