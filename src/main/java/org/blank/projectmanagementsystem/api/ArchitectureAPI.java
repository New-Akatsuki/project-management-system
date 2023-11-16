package org.blank.projectmanagementsystem.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.service.ArchitectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArchitectureAPI {
    private final ArchitectureService architectureService;

    @GetMapping("/pm/architectures")
    public ResponseEntity<List<Architecture>> getArchitectures() {
        List<Architecture> architectures = architectureService.getAllArchitectures();
        return ResponseEntity.ok(architectures);
    }
}
