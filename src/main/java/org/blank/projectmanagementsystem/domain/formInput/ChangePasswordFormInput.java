package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class ChangePasswordFormInput {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;


}
