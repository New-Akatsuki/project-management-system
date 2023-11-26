package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueSolveFormInput {
private Long id;
private String coAction;
private String preAction;
private String impact;
}
