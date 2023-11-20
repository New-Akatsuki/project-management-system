package org.blank.projectmanagementsystem.domain.formInput;

import jakarta.persistence.AssociationOverride;
import lombok.Data;

@Data
public class AddClientFormInput {
    private String name;
    private String email;
    private String phoneNumber;

    }
