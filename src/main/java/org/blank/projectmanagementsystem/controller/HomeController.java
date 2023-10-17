package org.blank.projectmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/createproject")
    public String CreateProject(){
        return "createproject";
    }

    @GetMapping("/projectlist")
    public String ProjectList(){
        return "projectlist";
    }

    @GetMapping("/userlist")
    public String UserList(){
        return "userlist";
    }



}
