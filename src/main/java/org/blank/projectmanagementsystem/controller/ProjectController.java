package org.blank.projectmanagementsystem.controller;

import org.springframework.ui.Model;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.ClientService;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.blank.projectmanagementsystem.service.ArchitectureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
@Data
@RequestMapping("/project")
public class ProjectController {
    private final DeliverableService deliverableService;
    private final SystemOutlineService systemOutlineService;
    private final ClientService clientService;
    private final ArchitectureService architectureService;

    @GetMapping("/overview")
    public String showProjectOverview(Model model) {
        List<Deliverable> deliverables = deliverableService.getAllDeliverables();
        model.addAttribute("deliverables", deliverables);
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        List<SystemOutline> systemOutlines = systemOutlineService.getAllSystemOutlines();
        model.addAttribute("systemOutlines", systemOutlines);
        List<Architecture> architectures = architectureService.getAllArchitectures();
        model.addAttribute("architectures", architectures);
        return "project-overview"; // Thymeleaf template name
    }
    @GetMapping("/project/system-outlines")
    public String showSystemOutlines(Model model) {
        List<SystemOutline> systemOutlines = systemOutlineService.getAllSystemOutlines();
        model.addAttribute("systemOutlines", systemOutlines);
        return "system-outline-overview"; // Thymeleaf template name
    }

    @PostMapping("/project/system-outline/save")
    public String saveSystemOutline(SystemOutline systemOutline) {
        systemOutlineService.save(systemOutline);
        return "redirect:/project/system-outlines";
    }




}
