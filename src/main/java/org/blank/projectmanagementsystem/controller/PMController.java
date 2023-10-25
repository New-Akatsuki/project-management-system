package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pm")
public class PMController {
    @GetMapping("/adduser")
    public ModelAndView addUser() {
        return new ModelAndView("add-user", "addUserFormInput", new AddUserFormInput());
    }
    @PostMapping("/adduser")
    public String addUser(@ModelAttribute AddUserFormInput addUserFormInput){
        return "redirect:/";

    }
    @GetMapping("/userlist")
    public String userList(){
        return "user-list";
    }
    @GetMapping("/userprofile")
    public ModelAndView  userprofile(){
        return new ModelAndView("user_profile","addUserFormInput",new AddUserFormInput());
    }
    @PostMapping("/userprofile")
    public String userprofile(@ModelAttribute AddUserFormInput addUserFormInput){
        return "user-list";
    }



}
