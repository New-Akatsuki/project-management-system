package org.blank.projectmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/changepassword")
    public String changepassword(){
        return "changepassword";
    }
    @GetMapping("/defaultpassword")
    public String defaultpassword(){
        return "defaultpassword";
    }
    @GetMapping("developerpage")
    public String developerpage(){
        return "developerpage";
    }

    @GetMapping("validate")
    public String validate(){return "validate";}


}
