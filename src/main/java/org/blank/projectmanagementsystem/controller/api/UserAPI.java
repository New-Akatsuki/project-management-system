package org.blank.projectmanagementsystem.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.UpdateUserFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j

public class UserAPI {
    private UserService userService;
    private DepartmentService departmentService;

    @PreAuthorize("hasAnyAuthority('PMO', 'PM')")
    @GetMapping("/pm/users")
    public ResponseEntity<List<UserViewObject>> getUserList() {
        List<UserViewObject> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/pm/member-create")
    public ResponseEntity<UserViewObject> addUser(@RequestBody AddUserFormInput addUserFormInput) {
        log.info("===================");
        log.info("User: {}", addUserFormInput);
        return ResponseEntity.ok(userService.createMember(addUserFormInput));
    }

    @PutMapping("/pm/member/status/{id}")
    public ResponseEntity<User> updateMemberStatus(@PathVariable Long id, @RequestParam boolean newStatus) {
        User user = userService.getUserById(id); // Change this to retrieve a specific user by id

        if (user != null) {
            user.setActive(newStatus);
            User updatedUser = userService.save(user); // Change Client to User since it seems like a typo
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/pm/user-edit/{id}")
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
}

