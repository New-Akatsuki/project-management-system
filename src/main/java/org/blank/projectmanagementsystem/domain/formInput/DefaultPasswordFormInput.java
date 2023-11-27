package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class DefaultPasswordFormInput {
    private String id;
    private String password;
    private String confirmPassword;
}
