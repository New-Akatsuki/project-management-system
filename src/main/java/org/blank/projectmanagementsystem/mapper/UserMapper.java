package org.blank.projectmanagementsystem.mapper;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;


public class UserMapper {

    public User mapToUser(AddUserFormInput addUserFormInput){


        return User.builder()
                .username(addUserFormInput.getUsername())
                .email(addUserFormInput.getEmail())
                .role(Role.valueOf(addUserFormInput.getRole()))
                .build();
    }
    public UserViewObject mapToUserViewObject(User user){
        return new UserViewObject(user);
    }
}
