package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;

import java.io.Serializable;

@Data
public class UserViewObject {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;
    private boolean active;

    public UserViewObject(User user) {
        name = user.getName();
        email=user.getEmail();
        id = user.getId();
        role = String.valueOf(user.getRole());
        department = user.getDepartment().getName();
        active= user.isActive();
    }
}


