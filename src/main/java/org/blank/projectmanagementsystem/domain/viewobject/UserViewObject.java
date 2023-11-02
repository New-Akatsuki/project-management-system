package org.blank.projectmanagementsystem.domain.viewobject;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;

public class UserViewObject {
    private String username;
    private String email;
    private String role;
    private String department;

    public UserViewObject(User user) {
        username=user.getUsername();
        email=user.getEmail();
        role= String.valueOf(user.getRole());
        Department department=user.getDepartment();

    }
    }


