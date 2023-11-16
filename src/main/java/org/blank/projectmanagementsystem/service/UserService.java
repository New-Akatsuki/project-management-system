package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;

import java.util.List;

public interface UserService {
    User save(User user);
    void saveDepartment(Department department);
    void changeDefaultPassword(String password);
    ChangePasswordFormInput changePassword(String currentPassword, String newPassword);
    User registerUser(AddUserFormInput addUserFormInput);
    List<UserViewObject> getAllUsers();

    Boolean checkCurrentPassword(String currentPassword);
    User getCurrentUser();
}
