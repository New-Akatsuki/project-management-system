package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.TestEntityClass;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class HomeController {

    private final UserService userService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/kan-ban")
    public String kanBanView(ModelMap model) {
        List<User> users =userService.getAllUser();
        model.addAttribute("users", users);
        return "test";
    }

    @GetMapping("/createproject")
    public String creatProjectView(){
        return "create-project";
    }

    @GetMapping("/issues")
    public String issuesView(){
        return "issue-page";
    }

    @GetMapping("/department")
    public String departmentView(){
        return "department";
    }
}
