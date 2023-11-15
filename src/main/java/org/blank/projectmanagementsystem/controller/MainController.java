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
import org.blank.projectmanagementsystem.utils.ImageEncoder;
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
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final UserService uService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping("/task-view")
    public String task() {
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
    public String userListView() {
        return "user-list";
    }


    @GetMapping("/department")
    public String departmentView() {
        return "department";
    }

    @GetMapping("/gantt")
    public String ganttView() {
        return "gantt-chart";
    }

    @GetMapping("/project-details-view")
    public String details() {
        return "project-details-info";
    }

    @GetMapping("/change-password")
    public String detailssd() {
        return "change-password";
    }

    @GetMapping("/all-issues")
    public String allIssue() {
        return "issue-all-display";
    }

    @GetMapping("/issue-create")
    public String issueCreate() {
        return "issue-create";
    }

    @GetMapping("/issue-display")
    public String issueDetails() {
        return "issue-all-display-test";
    }

    @GetMapping("/issue-details/{id}")
    public String issueDetailsView(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("id", id);
        return "issue-details";
    }

    @GetMapping("/issue-pic-view")
    public String issuePicView() {
        return "issue-pic-display";
    }


    @GetMapping("/issue-details-edit/{id}")
    public String editIssue(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("id", id);
        return "issue-edit";
    }

    @GetMapping("/issue-creator-view")
    public String issueCreatorView() {
        return "issue-creator-display";
    }

    @GetMapping("user-profile")
    public String userProfileView(Model model) {
        var user = uService.getCurrentUser();
        byte[] photoData = user.getPhotoData();

        // Always add the image attributes to the model, even if photoData is null
        String base64Image = (photoData != null) ? ImageEncoder.encodeToBase64(photoData) : null;
        String imageType = "image/jpeg"; // Set a default image type; you can modify this based on your application's needs

        model.addAttribute("base64Image", base64Image);
        model.addAttribute("imageType", imageType);
        model.addAttribute("currentUser", user);
        return "user-profile";
    }


    @PostMapping("/edit-user-in-profile")
    public String update(@RequestParam("name") String name,
                         @RequestParam("username") String username,
                         @RequestParam("email") String email,
                         @RequestParam("phone") String phone,
                         @RequestParam("userRole") String userRole,
                         @RequestParam("isNull") boolean isNull,
                         @RequestParam(name = "photoUrl", required = false) MultipartFile file, HttpSession session) throws IOException {

        var user = uService.getCurrentUser();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUserRole(userRole);

        // Check if a new photo is provided
        if (file != null && !file.isEmpty()) {
            byte[] photoUrl = file.getBytes();
            user.setPhotoData(photoUrl);
        }

        if(isNull){
            user.setPhotoData(null);
        }

        userRepository.save(user);

        var currentAuth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userRepository.save(user), user.getPassword(), currentAuth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/user-profile";
    }

}

