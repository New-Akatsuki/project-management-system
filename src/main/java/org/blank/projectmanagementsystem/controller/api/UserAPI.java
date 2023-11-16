package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.UpdateUserFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@Slf4j

public class UserAPI {
    private final UserService userService;
    private final DepartmentService departmentService;

    @PreAuthorize("hasAnyAuthority('PMO', 'PM','DH')")
    @GetMapping("/users")
    public ResponseEntity<List<UserViewObject>> getUserList() {
        List<UserViewObject> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/member-create")
    public ResponseEntity<UserViewObject> addUser(@RequestBody AddUserFormInput addUserFormInput) {
        log.info("===================");
        log.info("User: {}", addUserFormInput);
        return ResponseEntity.ok(userService.createMember(addUserFormInput));
    }

    @PutMapping("/member/status/{id}")
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

    @PreAuthorize("hasAnyAuthority('PMO', 'PM','DH')")
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
}

