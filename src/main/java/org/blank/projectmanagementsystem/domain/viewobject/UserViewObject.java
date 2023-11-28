package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserViewObject {

    private Long id;
    private String name;
    private String email;
    private String role;
    private Integer departmentId;
    private String department;
    private boolean active;

    public UserViewObject(User user) {
        name = user.getName();
        email=user.getEmail();
        id = user.getId();
        role = String.valueOf(user.getRole());
        departmentId = user.getDepartment().getId();
        department = user.getDepartment().getName();
        active= user.isActive();
    }

    
}


