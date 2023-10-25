package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class AddUserFormInput {
    private String name;
    private String username;
    private String role;
    private String department;
    private String image;
}
