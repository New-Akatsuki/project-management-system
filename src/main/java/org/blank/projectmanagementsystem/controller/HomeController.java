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

    @GetMapping("/issue3")
    public String issue3(){
        return "pic_issue_page";
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
}
