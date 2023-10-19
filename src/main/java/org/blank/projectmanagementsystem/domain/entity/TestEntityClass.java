package org.blank.projectmanagementsystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestEntityClass {
    private int issue_id;
    private String title;
    private Date date;
    private String issue_category;
    private String issue_place;
    private String issue_pic;
    private String content;
    private String res_party;
    private String dir_cause;
    private String root_cause;

    @Override
    public String toString() {
        return "TestEntityClass{" +
                "issue_id=" + issue_id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", issue_category='" + issue_category + '\'' +
                ", issue_place='" + issue_place + '\'' +
                ", issue_pic='" + issue_pic + '\'' +
                ", content='" + content + '\'' +
                ", res_party='" + res_party + '\'' +
                ", dir_cause='" + dir_cause + '\'' +
                ", root_cause='" + root_cause + '\'' +
                '}';
    }
}
