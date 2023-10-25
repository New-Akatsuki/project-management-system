package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class PMController {

    @GetMapping("/project-detail")
    public String ProjectDetail(){
        return "project-detail";
    }

    @GetMapping("/project-list")
    public String ProjectList(){
        return "project-list";
    }

    @GetMapping("/contract-info")
    public String ContractInfo(){
        return "contract-info";
    }

    @GetMapping("/department")
    public String Department(){
        return "department";
    }

    @GetMapping("/user-list")
    public String UserList(){
        return "user-list";
    }

}
