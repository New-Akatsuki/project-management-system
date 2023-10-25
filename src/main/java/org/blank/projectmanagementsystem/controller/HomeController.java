package org.blank.projectmanagementsystem.controller;

import org.blank.projectmanagementsystem.domain.entity.TestEntityClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/kan-ban")
    public String kanBanView() {
        return "test";
    }

    @GetMapping("/createproject")
    public String creatProjectView(){
        return "create-project";
    }

    @GetMapping("/user/add")
    public String userAddView(){
        return "add-user";
    }

    @GetMapping("/userProfile")
    public String userProfileView(){
        return "user-profile";
    }

    @GetMapping("/issues")
    public String issuesView(){
        return "issue-page";
    }
}
