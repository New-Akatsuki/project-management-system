package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.Login;
import org.blank.projectmanagementsystem.domain.entity.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    User getLoginUser();
    Optional<User> getEmail(String email);
    void saveDepartment(Department department);
    void changeDefaultPassword(String password);
    void changePassword(String currentPassword, String newPassword);


}
