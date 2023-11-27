package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.User;

@Data
public class Assignee {
    private long id;
    private String name;

    public Assignee(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
