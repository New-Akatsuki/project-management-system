package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Map;

public interface UserService {
    User save(User user);
    void saveDepartment(Department department);
    void changeDefaultPassword(String password);
    void changePassword(String currentPassword, String newPassword);
    User registerUser(AddUserFormInput addUserFormInput);
    List<UserViewObject> getAllUsers();

    User getLoginUser();
    Optional<User> getEmail(String email);
}
