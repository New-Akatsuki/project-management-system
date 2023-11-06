package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;

import java.io.Serializable;

@Data
public class UserViewObject {

    private Long id;
    private String name;
    private String role;
    private String department;

    public UserViewObject(User user) {
        name = user.getName();
        id = user.getId();
        role = String.valueOf(user.getRole());
        department = user.getDepartment().getName();
    }
}


