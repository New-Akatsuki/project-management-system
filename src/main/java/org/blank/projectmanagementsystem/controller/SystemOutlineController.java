package org.blank.projectmanagementsystem.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@Data
@RequestMapping("/systemOutline") // Base path for this controller
public class SystemOutlineController {

    private final SystemOutlineService systemOutlineService;



    @GetMapping("/add-system-outline")
    public String showSystemOutlineForm(Model model) {
        model.addAttribute("systemOutline", new SystemOutline());
        return "add-system-outline"; // Thymeleaf template name
    }

    @PostMapping("/save")
    public String saveSystemOutline(@ModelAttribute SystemOutline systemOutline) {
        if (systemOutline.getName() != null && !systemOutline.getName().isEmpty()) {
            // Valid name, save the systemOutline
            systemOutlineService.save(systemOutline);
            return "redirect:/systemOutline/system-outline-list";
        } else {
            // Invalid name, handle the error (redirect to an error page or show an error message)
            return "error"; // You can create an error Thymeleaf template for this
        }





    }


    @GetMapping("/{id}/edit")
    public String editSystemOutline(@PathVariable Long id, Model model) {
        SystemOutline systemOutline = systemOutlineService.findById(Math.toIntExact(id));
        model.addAttribute("systemOutline", systemOutline);
        return "edit-system-outline"; // You need to have an edit form template (edit-system-outline.html) for editing.
    }

    @GetMapping("/{id}/delete")
    public String deleteSystemOutline(@PathVariable Long id) {
        systemOutlineService.delete(Math.toIntExact(id));
        return "redirect:/systemOutline/system-outline-list";
    }

    @GetMapping("/system-outline-list")
    public String showSystemOutlineList(Model model) {
        List<SystemOutline> systemOutlines = systemOutlineService.getAllSystemOutlines();
        model.addAttribute("systemOutlines", systemOutlines);
        return "system-outline-list"; // Thymeleaf template name for displaying the list
    }
    @PostMapping("/system-outline-list")
    public String showSystemOutlineList(@ModelAttribute SystemOutline systemOutline) {
        // Validate if systemOutline.getName() is not null before saving
        if (systemOutline.getName() != null) {
            systemOutlineService.save(systemOutline);
            log.info("============================================");

            log.info("SystemOutline saved successfully");
            log.info("SystemOutline name: " + systemOutline.getName());

            log.info("============================================");
            return "redirect:/systemOutline/system-outline-list";
        } else {
            // Handle the case where name is null, show an error message or redirect to an error page
            return "error"; // Return appropriate error view
        }
    }
}
