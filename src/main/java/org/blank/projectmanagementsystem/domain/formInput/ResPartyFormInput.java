package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

import java.util.List;

@Data
public class ResPartyFormInput {
    private Long id;
    private List<Long> providerIds;
    private Long clientId;
}
