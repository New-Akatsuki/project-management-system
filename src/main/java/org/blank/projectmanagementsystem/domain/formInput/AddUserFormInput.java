package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class AddUserFormInput {

    private String username;
    private String email;
    private String role;
    private String department;

}
