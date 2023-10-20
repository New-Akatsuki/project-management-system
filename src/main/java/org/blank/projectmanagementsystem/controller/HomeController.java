package org.blank.projectmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/task-board")
    public String taskBoard(){
        return "board";
    }

    @GetMapping("/kan-ban")
    public String kabBan(){
        return "test";
    }


}
