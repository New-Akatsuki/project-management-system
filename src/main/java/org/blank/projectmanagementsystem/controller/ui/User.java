package org.blank.projectmanagementsystem.controller.ui;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequiredArgsConstructor
public class User {
    @GetMapping("/user-list")
    public String userList() {
        return "user-list";
    }

    @GetMapping("/user-list-pm")
    public String userListPM() {
        return "user-list-pm";
    }
    @GetMapping("/user-profile")
    public ModelAndView userprofile() {
        return new ModelAndView("user-profile", "addUserFormInput", new AddUserFormInput());
    }

}
