package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.UpdateUserFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAPI {
    private final UserService userService;
    private final DepartmentService departmentService;
    private final SessionRegistry sessionRegistry;

    @PreAuthorize("hasAnyAuthority('PMO','PM','DH')")
    @GetMapping("/get-users")
    public ResponseEntity<List<UserViewObject>> getUserList() {
        List<UserViewObject> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-active-user")
    public ResponseEntity<List<UserViewObject>> getActiveUsers() {
        List<UserViewObject> users = userService.getAllUsers().stream().filter(UserViewObject::isActive).toList();
        return ResponseEntity.ok(users);
    }

    String generateDefaultPassword() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    @PostMapping("/member-create")
    public ResponseEntity<UserViewObject> addUser(@RequestBody AddUserFormInput addUserFormInput) {
        return ResponseEntity.ok(new UserViewObject(userService.createMember(addUserFormInput, generateDefaultPassword())));
    }

    @PutMapping("/member/status/{id}")
    public ResponseEntity<User> updateMemberStatus(@PathVariable Long id, @RequestParam boolean newStatus) {
        User user = userService.getUserById(id); // Change this to retrieve a specific user by id
        if (user != null) {
            user.setActive(newStatus);
            User updatedUser = userService.save(user);
            if(!updatedUser.isActive()){
                logoutUser(updatedUser.getUsername());
            }
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private void logoutUser(String username) {
        List<Object> loggedUsers = sessionRegistry.getAllPrincipals();
        for (Object principal : loggedUsers) {
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation sessionInformation : sessions) {
                        sessionInformation.expireNow();
                    }
                }
            }
        }
    }

    @PreAuthorize("hasAnyAuthority('PMO','PM','DH')")
    @PutMapping("/user-edit/{id}")
    public ResponseEntity<UserViewObject> updateUser(@RequestBody UpdateUserFormInput user) {
        User existingUser = userService.getUserById(user.getId());
        Department department = departmentService.getDepartmentById(user.getDepartment());
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setDepartment(department);
            existingUser.setRole(Role.valueOf(user.getRole()));
            User updatedUser = userService.save(existingUser);
            return ResponseEntity.ok(new UserViewObject(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get-department")
    public List<Department> getDepartment() {
        List<Department> department = departmentService.getAllDepartments();
        log.info("get department{} \n\n ", department);
        return department;
    }

    @PostMapping("/users/change-password")
    public ResponseEntity<ChangePasswordFormInput> changePassword(@RequestBody ChangePasswordFormInput changePasswordFormInput) {
        ChangePasswordFormInput chpwd = userService.changePassword(
                changePasswordFormInput.getCurrentPassword(),
                changePasswordFormInput.getNewPassword());
        if (chpwd != null) {
            chpwd.setCurrentPassword(changePasswordFormInput.getCurrentPassword());
            chpwd.setNewPassword(changePasswordFormInput.getNewPassword());
            chpwd.setConfirmPassword(changePasswordFormInput.getConfirmPassword());
            return ResponseEntity.ok(chpwd);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/check-current-password")
    public Boolean checkCurrentPassword(@RequestBody ChangePasswordFormInput changePasswordFormInput) {
        return userService.checkCurrentPassword(changePasswordFormInput.getCurrentPassword());
    }

    @PostMapping("/check-user-by-username")
    public ResponseEntity<Boolean> checkUserByUsernameOrEmail(@RequestParam String data) {
        return ResponseEntity.ok(userService.checkUserExistOrNotWithUsername(data));
    }
}

