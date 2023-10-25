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

    @GetMapping("/task-view")
    public String task(){
        return "task-view";
    }
}
