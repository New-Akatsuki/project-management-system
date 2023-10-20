package org.blank.projectmanagementsystem.controller;

import org.blank.projectmanagementsystem.domain.entity.TestEntityClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/issue1")
    public String issue1(){
        return "issue_page";
    }

    @GetMapping("/issue2")
    public String issue2(Model model){
        model.addAttribute("model", new TestEntityClass());
        return "create_issue_page";
    }

    @GetMapping("/task-board")
    public String taskBoard(){
        return "board";
    }

    @GetMapping("/kan-ban")
    public String kabBan(){
        return "test";
    }


    @GetMapping("/display_issue")
    public String issue_display(){
        return "issue_display_page";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("model")TestEntityClass model){
        System.out.println(model);
        return "issue_display_page";
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

    @GetMapping("/projectdetail")
    public String ProjectDetail(){
        return "projectdetail";
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
