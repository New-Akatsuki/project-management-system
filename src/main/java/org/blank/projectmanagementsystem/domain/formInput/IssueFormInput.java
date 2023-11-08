package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.blank.projectmanagementsystem.domain.entity.IssuePlace;
import org.blank.projectmanagementsystem.domain.entity.User;

@Data
public class IssueFormInput {
    private Long id;

    private String title;

    private String content;

    private String directCause;

    private String rootCause;

    private ResPartyFormInput responsibleParty;

    private Long issuePlace;

    private Long issueCategory;

    private Long pic;
}
