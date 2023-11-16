package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ProfileEditFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    void saveDepartment(Department department);
    void changeDefaultPassword(String password);
    void changePassword(String currentPassword, String newPassword);
    User createMember(AddUserFormInput addUserFormInput);
    List<UserViewObject> getAllUsers();
    User getCurrentUser();
    User editUserProfile(ProfileEditFormInput profileEditFormInput);

    User getLoginUser();
    Optional<User> getEmail(String email);
    void updatePassword(String newPassword);
}
