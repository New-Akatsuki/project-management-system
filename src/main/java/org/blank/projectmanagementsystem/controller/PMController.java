package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pm")
public class PMController {
    @RequestMapping("/project-details-info")
    public String dashboard() {
        return "project-details-info";
    }



}
