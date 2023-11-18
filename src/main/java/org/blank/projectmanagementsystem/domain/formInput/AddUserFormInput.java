package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Department;

@Data
public class AddUserFormInput {

    private String name;
    private String email;
    private String role;
    private Long department;

}
