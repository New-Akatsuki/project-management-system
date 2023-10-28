package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);
    void saveDepartment(Department department);
    void changeDefaultPassword(String password);
    void changePassword(String currentPassword, String newPassword);

    List<User> getAllUser();
}
