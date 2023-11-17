package org.blank.projectmanagementsystem.controller.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.service.ClientService;
import org.blank.projectmanagementsystem.service.DepartmentService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final ClientService clientService;
    private final DepartmentService departmentService;

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(ModelMap model){
        var user = userService.getCurrentUser();
        if(user.getRole()== Role.MEMBER){
            return "redirect:/projects";
        }
        long userCount = userService.getAllUsers().size();
        long clientCount = clientService.getAllClients().size();
        long departmentCount = departmentService.getAllDepartments().size();
        model.addAttribute("userCount", userCount);
        model.addAttribute("clientCount", clientCount);
        model.addAttribute("departmentCount", departmentCount);

        return "index";
    }


    @PostMapping("/process_login")
    public ModelAndView processLogin() {
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "not-found";
    }


}
