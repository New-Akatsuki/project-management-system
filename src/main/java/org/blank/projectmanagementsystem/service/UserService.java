package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;

public interface UserService {
    User save(User user);
    void saveDepartment(Department department);
    void changeDefaultPassword(String password);
    void changePassword(String currentPassword, String newPassword);
     User registerUser(AddUserFormInput addUserFormInput);
}
