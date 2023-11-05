package org.blank.projectmanagementsystem.domain.formInput;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.Issue;

@Data
public class IssueCreateFormInput {
    private String id;
    private String title;
    private int category;
    private String place;
    private String pic;
    private String content;
    private String resParty;
    private String dirCause;
    private String rootCause;
    
//
//    public Issue toIssue() {
//        return Issue.builder()
//                .title(title)
//                .category(category)
//                .place(place)
//                .pic(pic)
//                .content(content)
//                .resParty(resParty)
//                .dirCause(dirCause)
//                .rootCause(rootCause)
//                .build();
//    }
}
