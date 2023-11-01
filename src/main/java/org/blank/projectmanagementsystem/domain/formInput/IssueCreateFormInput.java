package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;

@Data
public class IssueCreateFormInput {
    private String title;
    private String category;
    private String place;
    private String pic;
    private String content;
    private String resParty;
    private String dirCause;
    private String rootCause;
}
