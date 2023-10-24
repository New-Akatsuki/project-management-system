package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class DefaultPasswordFormInput {
    private String newPassword;
    private String confirmPassword;
}
