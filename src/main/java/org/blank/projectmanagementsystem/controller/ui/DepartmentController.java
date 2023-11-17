package org.blank.projectmanagementsystem.controller.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DepartmentController {
    @GetMapping("/department")
    public String Department() {
        return "department";
    }
}
