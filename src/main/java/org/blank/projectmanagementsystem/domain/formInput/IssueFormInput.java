package org.blank.projectmanagementsystem.domain.formInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.IssueCategory;
import org.blank.projectmanagementsystem.domain.entity.IssuePlace;
import org.blank.projectmanagementsystem.domain.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueFormInput {
    private Long id;

    private String title;

    private String content;

    private String directCause;

    private String rootCause;

    private Long responsibleParty;

    private Long issuePlace;

    private Long issueCategory;

    private Long pic;
}
