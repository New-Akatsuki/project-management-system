package org.blank.projectmanagementsystem.controller.ui;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.service.UserService;
import org.blank.projectmanagementsystem.utils.ImageEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService uService;

    @GetMapping("/user-list")
    public String userList() {
        return "user-list";
    }

    @GetMapping("/user-list-pm")
    public String userListPM() {
        return "user-list-pm";
    }

    @GetMapping("/user-list-dh")
    public String userListDH(){return "user-list-dh";}

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

        var updatedUser = uService.save(user);

        var currentAuth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(updatedUser, user.getPassword(), currentAuth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/user-profile";
    }


}
