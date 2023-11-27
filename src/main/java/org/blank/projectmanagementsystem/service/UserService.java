package org.blank.projectmanagementsystem.service;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ProfileEditFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    void saveDepartment(Department department);
    User getUserById(Long id);
    ChangePasswordFormInput changePassword(String currentPassword, String newPassword);
    Boolean checkCurrentPassword(String currentPassword);
    User getCurrentUser();
    User createMember(AddUserFormInput addUserFormInput,String defaultPassword);
    List<UserViewObject> getAllUsers();
    Optional<User> getEmail(String email);
    void updatePassword(String newPassword);
    Boolean checkUserExistOrNotWithUsername(String username);
}
