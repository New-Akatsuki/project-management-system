package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class UpdateUserFormInput {
    private Long id;
    private String name;
    private String email;
    private int department;
    private String Role;
}
