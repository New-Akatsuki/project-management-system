package org.blank.projectmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/userview")
    public String userview(){
        return "userview";

    }
    @GetMapping("/adduser")
    public String adduser(){
        return "adduser";

    }
    @GetMapping("/userprofile")
    public String userprofile(){
        return "userprofile";

    }

    @GetMapping("/createproject")
    public String createproject(){
        return "createproject";

    }



}
