package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Department;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserFormInput {

    private String name;
    private String email;
    private String role;
    private Long department;

}
