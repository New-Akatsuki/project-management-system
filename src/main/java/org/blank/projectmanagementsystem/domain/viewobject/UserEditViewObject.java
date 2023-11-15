package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;

@Data
public class UserEditViewObject {
    private Long id;
    private String photoUrl;
    private String fullName;
    private String userName;
    private String userRole;
    private String phone;
    private String email;

    public UserEditViewObject(Long id, String photoUrl, String fullName, String userName, String userRole, String phone, String email) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.fullName = fullName;
        this.userName = userName;
        this.userRole = userRole;
        this.phone = phone;
        this.email = email;
    }
}
