package org.blank.projectmanagementsystem.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@Data

public class DeliverableController {

    private final DeliverableService deliverableService;

    @GetMapping("add-deliverable")
    public String showDeliverableForm(Model model) {
        model.addAttribute("deliverable", new Deliverable());
        return "add-deliverable";
    }

    @PostMapping("/deliverable/save")
    public String saveDeliverable(@ModelAttribute Deliverable deliverable) {
        if (deliverable.getName() != null && !deliverable.getName().isEmpty()) {
            // Valid name, save the deliverable
            deliverableService.save(deliverable);
            return "redirect:/systemOutline/system-outline-list";

        } else {
            // Invalid name, handle the error (redirect to an error page or show an error message)
            return "error"; // You can create an error Thymeleaf template for this
        }
    }

    @GetMapping("/deliverable/{id}/edit")
    public String editDeliverable(@PathVariable Long id, Model model) {
        Deliverable deliverable = deliverableService.findById(Math.toIntExact(id));
        model.addAttribute("deliverable", deliverable);
        return "edit-deliverable"; // You need to have an edit form template (edit-deliverable.html) for editing.
    }

    @PostMapping("/deliverable/{id}/edit")
    public String editDeliverable(@PathVariable Long id, @ModelAttribute Deliverable deliverable) {
        deliverableService.save(deliverable);
        return "redirect:/deliverable/deliverable-list";
    }


    @GetMapping("/deliverable-list")
    public String showDeliverableList(Model model) {
        List<Deliverable> deliverables = deliverableService.getAllDeliverable();
        model.addAttribute("deliverables", deliverables);
        return "deliverable-list";
    }

    @PostMapping("/deliverable-list")
    public String showDeliverableList(@ModelAttribute Deliverable deliverable, Model model) {
        if (deliverable.getName() != null && !deliverable.getName().isEmpty()) {
            deliverableService.save(deliverable);
            return "redirect:/deliverable/deliverable-list";

        } else {
            return "error";
        }
    }

    }
