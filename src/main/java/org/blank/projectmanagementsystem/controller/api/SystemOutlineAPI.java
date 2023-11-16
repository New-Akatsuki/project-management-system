package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class SystemOutlineAPI {
    private final SystemOutlineService systemOutlineService;

    @GetMapping("/pm/system-outlines")
    public ResponseEntity<List<SystemOutline>> getSystemOutlines() {
        List<SystemOutline> systemOutlines = systemOutlineService.getAllSystemOutlines();
        return ResponseEntity.ok(systemOutlines);
    }
}
