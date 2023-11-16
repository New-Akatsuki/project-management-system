package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEditFormInput {
    private byte[] photoData;
    private String fullName;
    private String userName;
    private String userRole;
    private String phone;
    private String email;
}
