package org.blank.projectmanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.EditUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ProfileEditFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.IssueDetailsViewObject;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.IssueService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final UserService uService;
    private final UserRepository userRepository;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping("/task-view")
    public String task(){
        return "task-view";
    }

    @GetMapping("/project-view")
    public String project() {
        return "project-view";
    }
    @GetMapping("/kan-ban")
    public String kanBanView(ModelMap model) {
        return "project-view";
    }

    @GetMapping("/user-list")
    public String userListView(){
        return "user-list";
    }


  
    @GetMapping("/department")
    public String departmentView(){
        return "department";
    }

    @GetMapping("/gantt")
    public String ganttView(){
        return "gantt-chart";
    }

    @GetMapping("/project-details-view")
    public String details(){
        return "project-details-info";
    }

    @GetMapping("/change-password")
    public String detailssd(){
        return "change-password";
    }

    @GetMapping("/all-issues")
    public String allIssue(){
        return "issue-all-display";
    }

    @GetMapping("/issue-create")
    public String issueCreate(){
        return "issue-create";
    }

    @GetMapping("/issue-display")
    public String issueDetails(){
        return "issue-all-display-test";
    }

    @GetMapping("/issue-details/{id}")
    public String issueDetailsView(@PathVariable String id, ModelMap modelMap){
        modelMap.addAttribute("id", id);
        return "issue-details";
    }

    @GetMapping("/issue-pic-view")
    public String issuePicView(){
        return "issue-pic-display";
    }

    @GetMapping("/issue-pic-details-view/{id}")
    public String issuePicDetailsView(@PathVariable String id, ModelMap modelMap){
        modelMap.addAttribute("id", id);
        return "issue-pic-details-view";
    }

    @GetMapping("/issue-details-edit/{id}")
    public String editIssue(@PathVariable String id, ModelMap modelMap){
        modelMap.addAttribute("id", id);
        return "issue-edit";
    }

    @GetMapping("/issue-creator-view")
    public String issueCreatorView(){
        return "issue-creator-display";
    }

    @GetMapping("user-profile")
    public String userProfileView(Model model){
        var user = uService.getCurrentUser();
        model.addAttribute("currentUser", user);
        return "user-profile";
    }

    @PostMapping("/edit-user-in-profile")
    public String update(
                            @RequestParam("name") String name,
                            @RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("phone") String phone,
                            @RequestParam("userRole") String userRole,

                         @RequestParam(name = "photoUrl", required = false) MultipartFile file, HttpSession session) throws IOException {

        var user = uService.getCurrentUser();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUserRole(userRole);

        if (file != null && !file.isEmpty()) {
            try {
                //create unique filename
                String fileName = StringUtils.cleanPath(file.getOriginalFilename()).replace(" ","_");
                String uploadPath = "C:\\Users\\HP\\Downloads\\spring boot\\project-management-system\\src\\main\\resources\\static\\images"
                        + File.separator;
                Path path = Paths.get(uploadPath + fileName);
                user.setImgUrl("/images/"+fileName);

                // Save the file to the server
                try (OutputStream outputStream = Files.newOutputStream(path)) {
                    outputStream.write(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        var currentAuth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userRepository.save(user), user.getPassword(), currentAuth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user-profile";
    }
}

