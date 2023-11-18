package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class EditUserFormInput {
    private Long id;
    private String userName;
    private String email;
    private String role;
    private String department;
}

