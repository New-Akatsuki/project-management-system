package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class SystemOutlineAPI {
    private final SystemOutlineService systemOutlineService;

    @GetMapping("/system-outlines")
    public ResponseEntity<List<SystemOutline>> getSystemOutlines() {
        List<SystemOutline> systemOutlines = systemOutlineService.getAllSystemOutlineByStatusTrue();
        return ResponseEntity.ok(systemOutlines);
    }
    @PostMapping("/add-system-outline") // Changed endpoint
    public ResponseEntity<SystemOutline> addSystemOutline(@RequestBody SystemOutline systemOutline) {
        SystemOutline newSystemOutline = systemOutlineService.save(systemOutline);
        return ResponseEntity.ok(newSystemOutline);
    }

    @GetMapping("/system-outline/{id}")
    public ResponseEntity<SystemOutline> getSystemOutlineById(@PathVariable Long id) {
        SystemOutline systemOutline = systemOutlineService.getSystemOutlineById(id);
        if (systemOutline != null) {
            return ResponseEntity.ok(systemOutline);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/system-outline/update")
    public ResponseEntity<SystemOutline> updateSystemOutline(@RequestBody SystemOutline systemOutline) {
        SystemOutline existingSystemOutline = systemOutlineService.getSystemOutlineById(systemOutline.getId());

        if (existingSystemOutline != null) {
            // Update the existing systemOutline's information
            existingSystemOutline.setName(systemOutline.getName());

            SystemOutline updatedSystemOutline = systemOutlineService.save(existingSystemOutline);
            return ResponseEntity.ok(updatedSystemOutline); // Return the updated systemOutline
        } else {
            return ResponseEntity.notFound().build();

        }
    }


    @PutMapping("/system-outline/status/{id}")
    public ResponseEntity<SystemOutline> updateSystemOutlineStatus(@PathVariable Long id, @RequestParam boolean newStatus) {
        SystemOutline systemOutline = systemOutlineService.getSystemOutlineById(id);
        if (systemOutline != null) {
            systemOutline.setStatus(newStatus);
            SystemOutline updatedSystemOutline = systemOutlineService.save(systemOutline);

            return ResponseEntity.ok(updatedSystemOutline);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
